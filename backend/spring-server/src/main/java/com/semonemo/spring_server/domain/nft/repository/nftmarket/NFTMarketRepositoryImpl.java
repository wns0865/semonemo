package com.semonemo.spring_server.domain.nft.repository.nftmarket;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semonemo.spring_server.domain.nft.entity.NFTMarket;
import com.semonemo.spring_server.domain.nft.entity.QNFTMarket;

public class NFTMarketRepositoryImpl implements NFTMarketRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public NFTMarketRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QNFTMarket nftMarket = QNFTMarket.nFTMarket;

    @Override
    public List<NFTMarket> findSellingTopN(int size) {
        return queryFactory
            .selectFrom(nftMarket)
            .where(nftMarket.isSold.isFalse())
            .orderBy(nftMarket.marketId.desc())
            .limit(size)
            .fetch();
    }

    @Override
    public List<NFTMarket> findSellingNextN(Long cursorId, int size) {
        return queryFactory
            .selectFrom(nftMarket)
            .where(
                nftMarket.marketId.lt(cursorId)
                .and(nftMarket.isSold.isFalse())
            )
            .orderBy(nftMarket.marketId.desc())
            .limit(size)
            .fetch();
    }
}
