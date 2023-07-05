package com.travel.web_oasis.domain.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.travel.web_oasis.domain.entity.Post;
import com.travel.web_oasis.domain.posts.QPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void insertPosts() {

        IntStream.range(1,100).forEach(i -> {
            Post post = Post.builder()
                    .content("TestContent" + i)
                    .build();

            postRepository.save(post);
        });
    }


    @Test
    void updateTest() {

        Optional<Post> result = postRepository.findById(33L);

        if (result.isPresent()) {
            Post post = result.get();

            post.chaneContent("update Content");

            postRepository.save(post);
        }
    }


    @Test
    void testQueryDSL() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));

        //동적으로 처리하기 위해서 Q도메인 클래스를 얻어온다.
        QPost qPost = QPost.post;

        //QueryDSL에서 사용할 조건으로 여기선 위세어 바꾼 update로 설정
        String keyword = "update";

        //BooleanBuilder는 where문에 들어가는 조건들을 넣어주는 컨테이너라고 생각
        BooleanBuilder builder = new BooleanBuilder();

        //원하는 조건을 필드 값과 같이 결합해서 생성 !BooleanBuilder 안에 들어가야하는 값은 querydsl의 predicate 타입!
        BooleanExpression expression = qPost.content.contains(keyword);

        //만들어진 조건은 and나 or같은 키워드와 결합
        builder.and(expression);

        Page<Post> reuslt = postRepository.findAll(builder,pageable);

        reuslt.stream().forEach(post ->
                assertThat(post.getId()).isEqualTo(33L));
    }

    @Test
    void testQueryDSL2() {

        Pageable pageable = PageRequest.of(0, 5);

        QPost qPost = QPost.post;

        String findId = "3";
        String findContent = "Test";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exId = qPost.id.like("%"+findId+"%");

        BooleanExpression exContent = qPost.content.contains(findContent);

        BooleanExpression exAll = exId.and(exContent);

        builder.and(exAll);

        builder.or(qPost.id.eq(1L));

        //마지막에 추가한 1, 33은 content가 "update"이므로 제외
        Long[] expected = {1L,3L,13L,23L,30L,43L};


        Page<Post> result = postRepository.findAll(builder,pageable);

        AtomicInteger idx = new AtomicInteger();
        result.stream().forEach(post ->
                assertThat(post.getId()).isEqualTo(expected[idx.getAndIncrement()]));


    }
}