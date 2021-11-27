package ru.otus.pk.spring.dto;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.pk.spring.domain.Comment;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class CommentMapper {

    private final ModelMapper mapper;

    public Comment toEntity(CommentDto dto) {
        return isNull(dto) ? null : mapper.map(dto, Comment.class);
    }

    public CommentDto toDto(Comment entity) {
        return isNull(entity) ? null : mapper.map(entity, CommentDto.class);
    }
}
