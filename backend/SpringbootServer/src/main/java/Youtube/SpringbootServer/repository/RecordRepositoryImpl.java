package Youtube.SpringbootServer.repository;

import Youtube.SpringbootServer.dto.RecordDTO;
import Youtube.SpringbootServer.entity.Record;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static Youtube.SpringbootServer.entity.QRecord.*;
import static Youtube.SpringbootServer.entity.QVideoInformation.*;

public class RecordRepositoryImpl implements RecordRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public RecordRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

//    //분석 리스트 조회(querydsl)
//    @Override
//    public List<Record> search(@Param("id") long id) {
//        return queryFactory
//                .selectFrom(record)
//                .join(record.videoInformation, videoInformation).fetchJoin()
//                .where(record.member.id.eq(id))
//                .fetch();
//    }

    //분석 리스트 조회+ 페이징 + 검색 (querydsl)
    @Override
    public Page<RecordDTO> searchPage(long id, Pageable pageable, String searchText) {
        List<RecordDTO> content = queryFactory
                .select(Projections.constructor(RecordDTO.class,
                        record.id,
                        record.videoInformation.title,
                        record.createDate))
                .from(record)
                .join(record.videoInformation, videoInformation)
                .where(record.member.id.eq(id), searchContain(searchText))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //countquery 패치조인으로 최적화.
        JPAQuery<Record> countQuery = queryFactory
                .select(record)
                .from(record)
                .join(record.videoInformation, videoInformation).fetchJoin()
                .where(record.member.id.eq(id), searchContain(searchText));

        return PageableExecutionUtils.getPage(content, pageable, ()-> countQuery.fetch().size());
    }

    //검색어가 존재하면 contains 작동, 없으면 null 반환.
    private BooleanExpression searchContain(String searchText) {
        return searchText != null ? record.videoInformation.title.contains(searchText) :null;
    }
}
