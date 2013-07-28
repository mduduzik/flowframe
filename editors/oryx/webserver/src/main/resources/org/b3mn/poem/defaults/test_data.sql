insert into identity_(id, uri) values(1,'root'),(2,'public'),(3,'groups'),(4,'ownership');

insert into subject_(ident_id, nickname, email, fullname, dob, gender, postcode, first_login, last_login, login_count, language_code, country_code, password, visibility) values(2,NULL,NULL,NULL,NULL,NULL,NULL,'2008-01-01','2008-01-01',0,NULL,NULL,NULL,NULL);

insert into interaction_(id, subject, subject_descend, object, object_self, object_descend, object_restrict_to_parent, scheme, term) values(1,'U2',1,'U2',0,1,1,'http://b3mn.org/http','owner');

insert into plugin_(rel, title, description, java_class, is_export) values('/self','ModelHandler','Open model in the editor	org.b3mn.poem.handler.ModelHandler',1),
('/repository','RepositoryHandler','Returns the Repository base	org.b3mn.poem.handler.RepositoryHandler',0);

insert into setting_(subject_id, id, key, value) values(0,1,'UserManager.DefaultCountryCode','us'),(0,2,'UserManager.DefaultLanguageCode','en');

insert into structure_(hierarchy, ident_id) values('U',1),('U1',2),('U20',2),('U2',4),('U3',3);


