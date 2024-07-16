package br.com.billing.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBillingImportHistoryEntity is a Querydsl query type for BillingImportHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBillingImportHistoryEntity extends EntityPathBase<BillingImportHistoryEntity> {

    private static final long serialVersionUID = -1860994154L;

    public static final QBillingImportHistoryEntity billingImportHistoryEntity = new QBillingImportHistoryEntity("billingImportHistoryEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath blob = createString("blob");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath fileName = createString("fileName");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> importDate = createDateTime("importDate", java.time.LocalDateTime.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final EnumPath<br.com.billing.domain.enums.ImportStatus> status = createEnum("status", br.com.billing.domain.enums.ImportStatus.class);

    public QBillingImportHistoryEntity(String variable) {
        super(BillingImportHistoryEntity.class, forVariable(variable));
    }

    public QBillingImportHistoryEntity(Path<? extends BillingImportHistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBillingImportHistoryEntity(PathMetadata metadata) {
        super(BillingImportHistoryEntity.class, metadata);
    }

}

