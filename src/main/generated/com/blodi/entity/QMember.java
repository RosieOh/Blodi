package com.blodi.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1772327692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> active = createNumber("active", Integer.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> mid = createNumber("mid", Integer.class);

    public final StringPath mname = createString("mname");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath mpw = createString("mpw");

    public final StringPath nickname = createString("nickname");

    public final QProfile profile;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<com.blodi.entity.enums.MemberRole> role = createEnum("role", com.blodi.entity.enums.MemberRole.class);

    public final SetPath<com.blodi.entity.enums.MemberRole, EnumPath<com.blodi.entity.enums.MemberRole>> roleSet = this.<com.blodi.entity.enums.MemberRole, EnumPath<com.blodi.entity.enums.MemberRole>>createSet("roleSet", com.blodi.entity.enums.MemberRole.class, EnumPath.class, PathInits.DIRECT2);

    public final StringPath school = createString("school");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.profile = inits.isInitialized("profile") ? new QProfile(forProperty("profile"), inits.get("profile")) : null;
    }

}

