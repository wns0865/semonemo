package com.semonemo.spring_server.domain.asset.repository.assettag;

import static com.semonemo.spring_server.domain.asset.model.QAssetTag.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.semonemo.spring_server.domain.asset.model.AssetTag;
import com.semonemo.spring_server.domain.asset.model.QAtags;

public class AssetTagRepositoryImpl implements AssetTagRepositoryCustom {

	private JPAQueryFactory queryFactory;
	QAtags atags = QAtags.atags;
	public AssetTagRepositoryImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public List<AssetTag> findByAssetSellId(Long assetSellId) {
		return queryFactory
			.selectFrom(assetTag)
			.where(assetTag.assetSellId.eq(assetSellId))
			.fetch();
	}

	@Override
	public List<String> findTagsByAssetSellId(Long assetSellId) {
		return queryFactory
			.select(atags.name)
			.from(assetTag)
			.join(atags).on(assetTag.atagId.eq(atags.Id))
			.where(assetTag.assetSellId.eq(assetSellId))
			.fetch();
	}
}


