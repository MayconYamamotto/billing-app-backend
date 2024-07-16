package br.com.billing.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBillEntity is a Querydsl query type for BillEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBillEntity extends EntityPathBase<BillEntity> {

    private static final long serialVersionUID = -1397800727L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBillEntity billEntity = new QBillEntity("billEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath description = createString("description");

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final QBillingImportHistoryEntity importHistory;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final DatePath<java.time.LocalDate> paymentDate = createDate("paymentDate", java.time.LocalDate.class);

    public final EnumPath<br.com.billing.domain.enums.BillingStatus> status = createEnum("status", br.com.billing.domain.enums.BillingStatus.class);

    public final QUserEntity sysuser;

    public QBillEntity(String variable) {
        this(BillEntity.class, forVariable(variable), INITS);
    }

    public QBillEntity(Path<? extends BillEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBillEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBillEntity(PathMetadata metadata, PathInits inits) {
        this(BillEntity.class, metadata, inits);
    }

    public QBillEntity(Class<? extends BillEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.importHistory = inits.isInitialized("importHistory") ? new QBillingImportHistoryEntity(forProperty("importHistory")) : null;
        this.sysuser = inits.isInitialized("sysuser") ? new QUserEntity(forProperty("sysuser")) : null;
    }

}

