
    create table delegues (
        id binary(16) not null,
        region varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table directeurs (
        id binary(16) not null,
        id_bureau varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table directeurs_academie (
        id binary(16) not null,
        nom_academie varchar(255) not null,
        region varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table eleves (
        age integer not null,
        annee_inscription date not null,
        id binary(16) not null,
        parent_id binary(16),
        classe varchar(255) not null,
        id_eleve binary(16) not null,
        niveau varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table enseignants (
        annees_experience integer not null,
        id binary(16) not null,
        id_employe varchar(255) not null,
        matiere varchar(255) not null,
        qualification varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table notes (
        date_attribuee date not null,
        note float(23) not null,
        eleve_id binary(16),
        enseignant_id binary(16),
        id binary(16) not null,
        id_eleve binary(16) not null,
        id_enseignant binary(16) not null,
        id_note binary(16) not null,
        matiere varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table parents (
        nombre_enfants integer not null,
        id binary(16) not null,
        id_parent binary(16) not null,
        profession varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table presences (
        date date not null,
        eleve_id binary(16),
        enseignant_id binary(16),
        id binary(16) not null,
        id_eleve binary(16) not null,
        id_enseignant binary(16) not null,
        id_presence binary(16) not null,
        remarques varchar(255) not null,
        statut varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table rapports (
        date date not null,
        id binary(16) not null,
        utilisateur_id binary(16),
        contenu TEXT not null,
        genere_par varchar(255) not null,
        id_rapport binary(16) not null,
        type_rapport varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table ressources (
        date_telechargement datetime(6),
        enseignant_id binary(16),
        id binary(16) not null,
        description TEXT,
        id_ressource binary(16) not null,
        niveau varchar(255) not null,
        telecharge_par varchar(255) not null,
        titre varchar(255) not null,
        type_fichier varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table utilisateurs (
        id binary(16) not null,
        adresse varchar(255),
        email varchar(255) not null,
        mot_de_passe varchar(255) not null,
        nom varchar(255) not null,
        numero_de_telephone varchar(255),
        role varchar(255) not null,
        type_utilisateur varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table eleves 
       add constraint UK7t7i09twcdwk53m0kg0x3h96p unique (id_eleve);

    alter table enseignants 
       add constraint UKmhlcbxd5a705j44lo5thytfgw unique (id_employe);

    alter table parents 
       add constraint UKrai054jpshv261n0lepwjpjao unique (id_parent);

    alter table rapports 
       add constraint UK82t886se4yhu5yn1kxtgvjmki unique (id_rapport);

    alter table utilisateurs 
       add constraint UK6ldvumu3hqvnmmxy1b6lsxwqy unique (email);

    alter table delegues 
       add constraint FKgxswgckkcfraq541xqc3i535i 
       foreign key (id) 
       references utilisateurs (id);

    alter table directeurs 
       add constraint FK91gj3hejwm0dy7vldoq9s3e98 
       foreign key (id) 
       references utilisateurs (id);

    alter table directeurs_academie 
       add constraint FKppgxskahr232xa2n4f0jljdcg 
       foreign key (id) 
       references utilisateurs (id);

    alter table eleves 
       add constraint FKkngsjbf76w8504k24do2ddx8n 
       foreign key (parent_id) 
       references parents (id);

    alter table eleves 
       add constraint FKf69fkidfo8idpttwun3qhmnvy 
       foreign key (id) 
       references utilisateurs (id);

    alter table enseignants 
       add constraint FK2rbnr64c4s235se9whblgw0ns 
       foreign key (id) 
       references utilisateurs (id);

    alter table notes 
       add constraint FKprwbkef2x1ameyvc3oac5cr9q 
       foreign key (eleve_id) 
       references eleves (id);

    alter table notes 
       add constraint FK846f9b3lh8yrsrpgicnn3y8c5 
       foreign key (enseignant_id) 
       references enseignants (id);

    alter table parents 
       add constraint FKlwe6cg4cu95ei00ikhkuqtbf8 
       foreign key (id) 
       references utilisateurs (id);

    alter table presences 
       add constraint FKp74oyr8goy0la0d9eecq859ih 
       foreign key (eleve_id) 
       references eleves (id);

    alter table presences 
       add constraint FKjymhbva5kr3cxha9e7ougnayu 
       foreign key (enseignant_id) 
       references enseignants (id);

    alter table rapports 
       add constraint FK4kp7uua1vcmn7h0sk3uepscj9 
       foreign key (utilisateur_id) 
       references utilisateurs (id);

    alter table ressources 
       add constraint FKj7306e8koj02a1daaisnhe6l6 
       foreign key (enseignant_id) 
       references enseignants (id);

    create table delegues (
        id binary(16) not null,
        region varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table directeurs (
        id binary(16) not null,
        id_bureau varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table directeurs_academie (
        id binary(16) not null,
        nom_academie varchar(255) not null,
        region varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table eleves (
        age integer not null,
        annee_inscription date not null,
        id binary(16) not null,
        parent_id binary(16),
        classe varchar(255) not null,
        id_eleve varchar(255) not null,
        niveau varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table enseignants (
        annees_experience integer not null,
        id binary(16) not null,
        id_employe varchar(255) not null,
        matiere varchar(255) not null,
        qualification varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table notes (
        date_attribuee date not null,
        note float(23) not null,
        eleve_id binary(16),
        enseignant_id binary(16),
        id binary(16) not null,
        id_eleve varchar(255) not null,
        id_enseignant varchar(255) not null,
        id_note varchar(255) not null,
        matiere varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table parents (
        nombre_enfants integer not null,
        id binary(16) not null,
        id_parent varchar(255) not null,
        profession varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table presences (
        date date not null,
        eleve_id binary(16),
        enseignant_id binary(16),
        id binary(16) not null,
        id_eleve varchar(255) not null,
        id_enseignant varchar(255) not null,
        id_presence varchar(255) not null,
        remarques varchar(255) not null,
        statut varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table rapports (
        date date not null,
        id binary(16) not null,
        utilisateur_id binary(16),
        contenu TEXT not null,
        genere_par varchar(255) not null,
        id_rapport varchar(255) not null,
        type_rapport varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table ressources (
        date_telechargement datetime(6),
        enseignant_id binary(16),
        id binary(16) not null,
        description TEXT,
        id_ressource varchar(255) not null,
        niveau varchar(255) not null,
        telecharge_par varchar(255) not null,
        titre varchar(255) not null,
        type_fichier varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table utilisateurs (
        id binary(16) not null,
        adresse varchar(255),
        email varchar(255) not null,
        mot_de_passe varchar(255) not null,
        nom varchar(255) not null,
        numero_de_telephone varchar(255),
        role varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table eleves 
       add constraint UK7t7i09twcdwk53m0kg0x3h96p unique (id_eleve);

    alter table enseignants 
       add constraint UKmhlcbxd5a705j44lo5thytfgw unique (id_employe);

    alter table parents 
       add constraint UKrai054jpshv261n0lepwjpjao unique (id_parent);

    alter table rapports 
       add constraint UK82t886se4yhu5yn1kxtgvjmki unique (id_rapport);

    alter table utilisateurs 
       add constraint UK6ldvumu3hqvnmmxy1b6lsxwqy unique (email);

    alter table delegues 
       add constraint FKgxswgckkcfraq541xqc3i535i 
       foreign key (id) 
       references utilisateurs (id);

    alter table directeurs 
       add constraint FK91gj3hejwm0dy7vldoq9s3e98 
       foreign key (id) 
       references utilisateurs (id);

    alter table directeurs_academie 
       add constraint FKppgxskahr232xa2n4f0jljdcg 
       foreign key (id) 
       references utilisateurs (id);

    alter table eleves 
       add constraint FKkngsjbf76w8504k24do2ddx8n 
       foreign key (parent_id) 
       references parents (id);

    alter table eleves 
       add constraint FKf69fkidfo8idpttwun3qhmnvy 
       foreign key (id) 
       references utilisateurs (id);

    alter table enseignants 
       add constraint FK2rbnr64c4s235se9whblgw0ns 
       foreign key (id) 
       references utilisateurs (id);

    alter table notes 
       add constraint FKprwbkef2x1ameyvc3oac5cr9q 
       foreign key (eleve_id) 
       references eleves (id);

    alter table notes 
       add constraint FK846f9b3lh8yrsrpgicnn3y8c5 
       foreign key (enseignant_id) 
       references enseignants (id);

    alter table parents 
       add constraint FKlwe6cg4cu95ei00ikhkuqtbf8 
       foreign key (id) 
       references utilisateurs (id);

    alter table presences 
       add constraint FKp74oyr8goy0la0d9eecq859ih 
       foreign key (eleve_id) 
       references eleves (id);

    alter table presences 
       add constraint FKjymhbva5kr3cxha9e7ougnayu 
       foreign key (enseignant_id) 
       references enseignants (id);

    alter table rapports 
       add constraint FK4kp7uua1vcmn7h0sk3uepscj9 
       foreign key (utilisateur_id) 
       references utilisateurs (id);

    alter table ressources 
       add constraint FKj7306e8koj02a1daaisnhe6l6 
       foreign key (enseignant_id) 
       references enseignants (id);
