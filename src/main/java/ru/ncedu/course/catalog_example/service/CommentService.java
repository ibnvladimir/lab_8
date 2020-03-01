package ru.ncedu.course.catalog_example.service;

import ru.ncedu.course.catalog_example.exception.OfferingNotFoundException;
import ru.ncedu.course.catalog_example.exception.UnauthorizedException;
import ru.ncedu.course.catalog_example.model.dao.CommentDAO;
import ru.ncedu.course.catalog_example.model.dao.OfferingDAO;
import ru.ncedu.course.catalog_example.model.dto.CommentDTO;
import ru.ncedu.course.catalog_example.model.entity.CommentEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CommentService {

    @Inject
    private AuthorizationBean authorizationBean;

    @Inject
    private CommentDAO commentDAO;

    @Inject
    private OfferingDAO offeringDAO;

    public CommentDTO create(String message, long offering) throws UnauthorizedException, OfferingNotFoundException {
        CommentEntity entity = new CommentEntity();
        entity.setMessage(message);
        entity.setOffering(offeringDAO.findById(offering).orElseThrow(OfferingNotFoundException::new));
        entity.setAuthor(authorizationBean.getUser().orElseThrow(UnauthorizedException::new));
        commentDAO.create(entity);
        return new CommentDTO(entity);
    }

    public List<CommentDTO> findAllByOffering(long offering) {
        List<CommentEntity> comments = commentDAO.findByOfferingId(offering).stream().distinct().collect(Collectors.toList());
        List<CommentDTO> result = comments.stream().map(CommentDTO::new).collect(Collectors.toList());
        if (authorizationBean.isAuthorized()) {
            for (int i=0; i<result.size(); i++) {
                /*
                if(comments.get(i).getLikes().contains(authorizationBean.getUser())) {
                    result.get(i).setLiked(true);
                }
                */
                    for (int j = 0; j < comments.get(i).getLikes().size(); j++) {
                        if (comments.get(i).getLikes().get(j).getId() == authorizationBean.getUser().get().getId()) {
                            result.get(i).setLiked(true);
                        }
                    }
            }

        }
        return result;
    }

    public void addLike (CommentEntity entity) {
        boolean currentUserIsPresent = false;
        if (entity.getLikes().size() != 0) {
            for (int j = 0; j < entity.getLikes().size(); j++) {
                if (entity.getLikes().get(j).getId() == authorizationBean.getUser().get().getId()) {
                    entity.getLikes().remove(j);
                    currentUserIsPresent = true;
                    break;
                }
            }
            if (!currentUserIsPresent) {
                entity.getLikes().add(authorizationBean.getUser().get());
            }
        } else {
            entity.getLikes().add(authorizationBean.getUser().get());
        }
        commentDAO.update(entity);
    }

}
