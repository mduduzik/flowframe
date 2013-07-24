# Converted with pg2mysql-1.9
# Converted on Tue, 23 Jul 2013 22:06:43 -0400
# Lightbox Technologies Inc. http://www.lightbox.ca

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone="+00:00";

DROP TABLE IF EXISTS identity_;
CREATE TABLE identity_ (
    id int(11) NOT NULL,
    uri text NOT NULL
);

DROP TABLE IF EXISTS interaction_;
CREATE TABLE interaction_ (
    id int(11) NOT NULL,
    subject text NOT NULL,
    subject_descend bool DEFAULT 0 NOT NULL,
    object text NOT NULL,
    object_self bool DEFAULT 1 NOT NULL,
    object_descend bool DEFAULT 0 NOT NULL,
    object_restrict_to_parent bool DEFAULT 0 NOT NULL,
    scheme text NOT NULL,
    term text NOT NULL
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS structure_;
CREATE TABLE structure_ (
    hierarchy text NOT NULL,
    ident_id int(11) NOT NULL
) ;

DROP TABLE IF EXISTS comment_;
CREATE TABLE comment_ (
    id int(11) auto_increment NOT NULL,
    subject_id int(11) NOT NULL,
    title text,
    content text NOT NULL
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS content_;
CREATE TABLE content_ (
    id int(11) NOT NULL,
    erdf text NOT NULL,
    svg text,
    png_large BLOB,
    png_small BLOB
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS friend_;
CREATE TABLE friend_ (
    id int(11) NOT NULL,
    subject_id int(11) NOT NULL,
    friend_id int(11) NOT NULL,
    model_count int(11) DEFAULT 0 NOT NULL
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS model_rating_;
CREATE TABLE model_rating_ (
    id int(11) NOT NULL,
    subject_id int(11) NOT NULL,
    object_id int(11) NOT NULL,
    score int(11) NOT NULL
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS plugin_;
CREATE TABLE plugin_ (
    rel text NOT NULL,
    title text NOT NULL,
    description text NOT NULL,
    java_class text NOT NULL,
    is_export bool NOT NULL
);

DROP TABLE IF EXISTS representation_;
CREATE TABLE representation_ (
    id int(11) NOT NULL,
    ident_id int(11) NOT NULL,
    mime_type text NOT NULL,
    language varchar(255) DEFAULT 'en_US',
    title varchar(255) DEFAULT '',
    summary varchar(255) DEFAULT '',
    created datetime NULL,
    updated datetime NULL,
    `type` varchar(255) DEFAULT 'undefined'
, PRIMARY KEY(`id`)
);

DELIMITER //
DROP TRIGGER IF EXISTS representation_INSERT//
CREATE TRIGGER `representation_INSERT` BEFORE INSERT ON `representation_`
FOR EACH ROW BEGIN
        -- Set the creation date
    SET new.created = now();

        -- Set the udpate date
    Set new.updated = now();
END//

DROP TRIGGER IF EXISTS `representation_UPDATE`//
CREATE TRIGGER `representation_UPDATE` BEFORE UPDATE ON `representation_`
FOR EACH ROW BEGIN
        -- Set the udpate date
    Set new.updated = now();
END//
DELIMITER ;

DROP TABLE IF EXISTS schema_info_;
CREATE TABLE schema_info_ (
    version int(11)
);

DROP TABLE IF EXISTS setting_;
CREATE TABLE setting_ (
    subject_id int(11),
    id int(11) auto_increment NOT NULL,
    key_ text NOT NULL,
    value text NOT NULL
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS subject_;
CREATE TABLE subject_ (
    ident_id int(11) NOT NULL,
    nickname text,
    email text,
    fullname text,
    dob date,
    gender text,
    postcode text,
    first_login date NOT NULL,
    last_login date NOT NULL,
    login_count int(11) DEFAULT 0 NOT NULL,
    language_code text,
    country_code text,
    password text,
    visibility text
, PRIMARY KEY(`ident_id`)
);

DROP TABLE IF EXISTS tag_definition_;
CREATE TABLE tag_definition_ (
    id int(11) auto_increment NOT NULL,
    subject_id int(11) NOT NULL,
    name text NOT NULL
, PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS tag_relation_;
CREATE TABLE tag_relation_ (
    id int(11) auto_increment NOT NULL,
    tag_id int(11) NOT NULL,
    object_id int(11) NOT NULL
, PRIMARY KEY(`id`)
);

ALTER TABLE `friend_` ADD INDEX ( subject_id, friend_id ) ;

DROP VIEW IF EXISTS access_;
CREATE OR REPLACE VIEW access_ AS 
 SELECT context_name.id AS context_id, context_name.uri AS context_name, subject_name.id AS subject_id, subject_name.uri AS subject_name, object_name.id AS object_id, object_name.uri AS object_name, access.id AS access_id, access.scheme AS access_scheme, access.term AS access_term
   FROM interaction_ access, structure_ context, identity_ context_name, structure_ subject_axis, identity_ subject_name, structure_ object_axis, identity_ object_name
  WHERE access.subject = context.hierarchy AND context.ident_id = context_name.id AND (access.subject = subject_axis.hierarchy OR access.subject_descend) AND (NOT access.object_restrict_to_parent AND access.object_self AND access.object = object_axis.hierarchy OR NOT access.object_restrict_to_parent AND access.object_descend  OR access.object_restrict_to_parent AND access.object_self AND object_axis.hierarchy = subject_axis.hierarchy OR access.object_restrict_to_parent AND access.object_descend) AND subject_axis.ident_id = subject_name.id AND object_axis.ident_id = object_name.id;
