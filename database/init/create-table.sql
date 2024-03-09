Hibernate:
create table baby (
                      id bigint not null auto_increment,
                      gender enum ('FEMALE','MALE') not null,
                      name varchar(255) not null,
                      baby_profile_id bigint,
                      primary key (id)
) engine=InnoDB
Hibernate:
create table baby_profile (
                              id bigint not null auto_increment,
                              due_at date not null,
                              image_url TEXT,
                              pregnant_at date not null,
                              twins integer not null,
                              user_id bigint,
                              primary key (id)
) engine=InnoDB
Hibernate:
create table blood_sugar_record (
                                    id bigint not null auto_increment,
                                    created_at datetime(6),
                                    modified_at datetime(6),
                                    blood_sugar_record_type enum ('AFTER_BREAKFAST','AFTER_DINNER','AFTER_LUNCH','BEFORE_BREAKFAST','BEFORE_DINNER','BEFORE_LUNCH','BEFORE_SLEEP','RANDOM') not null,
                                    level integer not null,
                                    record_at date,
                                    user_id bigint,
                                    primary key (id)
) engine=InnoDB
Hibernate:
create table comment (
                         id bigint not null auto_increment,
                         created_at datetime(6),
                         modified_at datetime(6),
                         content TEXT not null,
                         is_deleted bit not null,
                         parent_comment_id bigint,
                         cozy_log_id bigint,
                         user_id bigint,
                         primary key (id)
) engine=InnoDB
Hibernate:
create table cozy_log (
                          id bigint not null auto_increment,
                          created_at datetime(6),
                          modified_at datetime(6),
                          content TEXT NOT NULL,
                          FULLTEXT INDEX full_text_idx_content (content) WITH PARSER ngram,
                          mode enum ('PRIVATE','PUBLIC') not null,
                          title VARCHAR(100) NOT NULL,
                          FULLTEXT INDEX full_text_idx_title (title) WITH PARSER ngram,
                          view bigint,
                          user_id bigint,
                          primary key (id)
) engine=InnoDB
Hibernate:
create table cozy_log_image (
                                id bigint not null auto_increment,
                                description varchar(255),
                                image_url TEXT not null,
                                cozy_log_id bigint,
                                primary key (id)
) engine=InnoDB
Hibernate:
create table growth_diary (
                              id bigint not null auto_increment,
                              content TEXT,
                              image_url TEXT,
                              title TEXT,
                              baby_profile_id bigint,
                              growth_report_id bigint,
                              primary key (id)
) engine=InnoDB
Hibernate:
create table growth_record (
                               id bigint not null auto_increment,
                               abdomen_circum float(53),
                               head_circum float(53),
                               head_diameter float(53),
                               thigh_length float(53),
                               weight float(53),
                               baby_id bigint,
                               growth_report_id bigint,
                               primary key (id)
) engine=InnoDB
Hibernate:
create table growth_report (
                               id bigint not null auto_increment,
                               created_at datetime(6),
                               modified_at datetime(6),
                               record_at date,
                               growth_diary_id bigint,
                               primary key (id)
) engine=InnoDB
Hibernate:
create table likes (
                       id bigint not null auto_increment,
                       created_at datetime(6),
                       modified_at datetime(6),
                       cozy_log_id bigint,
                       user_id bigint,
                       primary key (id)
) engine=InnoDB
Hibernate:
create table meal_record (
                             id bigint not null auto_increment,
                             created_at datetime(6),
                             modified_at datetime(6),
                             image_url varchar(255) not null,
                             meal_type enum ('BREAKFAST','DINNER','LUNCH','SNACK') not null,
                             record_at datetime(6) not null,
                             user_id bigint,
                             primary key (id)
) engine=InnoDB
Hibernate:
create table scrap (
                       id bigint not null auto_increment,
                       created_at datetime(6),
                       modified_at datetime(6),
                       cozy_log_id bigint,
                       user_id bigint,
                       primary key (id)
) engine=InnoDB
Hibernate:
create table supplement (
                            id bigint not null auto_increment,
                            created_at datetime(6),
                            modified_at datetime(6),
                            name varchar(255) not null,
                            target_count integer not null,
                            user_id bigint,
                            primary key (id)
) engine=InnoDB
Hibernate:
create table supplement_record (
                                   id bigint not null auto_increment,
                                   created_at datetime(6),
                                   modified_at datetime(6),
                                   record_at datetime(6),
                                   supplement_id bigint,
                                   primary key (id)
) engine=InnoDB
Hibernate:
create table user (
                      id bigint not null auto_increment,
                      created_at datetime(6),
                      modified_at datetime(6),
                      birth date,
                      email varchar(255) not null,
                      introduce VARCHAR(255),
                      name varchar(255) not null,
                      nickname varchar(255) not null,
                      profile_image_url varchar(255) not null,
                      recent_baby_profild_id bigint,
                      user_type enum ('BABY','DAD','MOM') not null,
                      primary key (id)
) engine=InnoDB
Hibernate:
create table weight_record (
                               id bigint not null auto_increment,
                               created_at datetime(6),
                               modified_at datetime(6),
                               record_at date not null,
                               weight float(53) not null,
                               user_id bigint,
                               primary key (id)
) engine=InnoDB
Hibernate:
alter table growth_diary
drop index UK_9pqit6e5jo29kgpfgd7c1bmmg
    Hibernate:
alter table growth_diary
    add constraint UK_9pqit6e5jo29kgpfgd7c1bmmg unique (growth_report_id)
    Hibernate:
alter table growth_report
drop index UK_oggt5ttpohnhye77tw52yl0it
    Hibernate:
alter table growth_report
    add constraint UK_oggt5ttpohnhye77tw52yl0it unique (growth_diary_id)
    Hibernate:
alter table user
drop index nickname_email_unique
    Hibernate:
alter table user
    add constraint nickname_email_unique unique (nickname, email)
    Hibernate:
alter table baby
    add constraint FK95hjcpq6iq34gio6xpkytyf0f
        foreign key (baby_profile_id)
            references baby_profile (id)
    Hibernate:
alter table baby_profile
    add constraint FKhpxwje555d8hn7a587v9vd9o
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table blood_sugar_record
    add constraint FK78mww376oy7sji2as02ilfsbr
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table comment
    add constraint FKb0awewjl3a9gmn6952dt1t3xq
        foreign key (cozy_log_id)
            references cozy_log (id)
    Hibernate:
alter table comment
    add constraint FK8kcum44fvpupyw6f5baccx25c
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table cozy_log
    add constraint FKqojm4o1g1gl6d6ojixonlu0ad
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table cozy_log_image
    add constraint FKti3lsgpgt4n0nm628n5r8jak3
        foreign key (cozy_log_id)
            references cozy_log (id)
    Hibernate:
alter table growth_diary
    add constraint FK43ebt123jhhro1i947gi73dud
        foreign key (baby_profile_id)
            references baby_profile (id)
    Hibernate:
alter table growth_diary
    add constraint FKegxx9eu9tlcmy5b2fuqnruuv7
        foreign key (growth_report_id)
            references growth_report (id)
    Hibernate:
alter table growth_record
    add constraint FKhc6idng4wkndxo8ra2bjm51vk
        foreign key (baby_id)
            references baby (id)
    Hibernate:
alter table growth_record
    add constraint FKt5v9n7tt8orsihumg9chbd4j1
        foreign key (growth_report_id)
            references growth_report (id)
    Hibernate:
alter table growth_report
    add constraint FK142amoyg9j1dk9iji5g9hbdwj
        foreign key (growth_diary_id)
            references growth_diary (id)
    Hibernate:
alter table likes
    add constraint FKiv5ut6cuv41ltspnge7i7aypb
        foreign key (cozy_log_id)
            references cozy_log (id)
    Hibernate:
alter table likes
    add constraint FKi2wo4dyk4rok7v4kak8sgkwx0
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table meal_record
    add constraint FKh08dwow9sujosvsh2ajpsixtv
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table scrap
    add constraint FKgt91kwgqa4f4oaoi9ljgy75mw
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table supplement
    add constraint FKkuwv1aisu2w0kfb01sgbkdd66
        foreign key (user_id)
            references user (id)
    Hibernate:
alter table supplement_record
    add constraint FKr0ulvl1nllhc8fiynrm8csoge
        foreign key (supplement_id)
            references supplement (id)
    Hibernate:
alter table weight_record
    add constraint FK3ao5qnxclhjwocafngi1m4au1
        foreign key (user_id)
            references user (id)