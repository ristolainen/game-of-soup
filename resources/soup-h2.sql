
DROP TABLE IF EXISTS action_type;
CREATE TABLE action_type (
  id varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO action_type VALUES ('antidote'),('arsenic'),('cyanide'),('glass'),('none'),('switch');

DROP TABLE IF EXISTS action_result;
CREATE TABLE action_result (
  id varchar(10) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO action_result VALUES ('alive'),('dead');

DROP TABLE IF EXISTS game_status;
CREATE TABLE game_status (
  id varchar(45) NOT NULL,
  PRIMARY KEY (id)
);

INSERT INTO game_status VALUES ('finished'),('in_progress'),('new');

DROP TABLE IF EXISTS game;
CREATE TABLE game (
  id int(11) NOT NULL AUTO_INCREMENT,
  status varchar(10) NOT NULL,
  started datetime NOT NULL,
  ended datetime DEFAULT NULL,
  PRIMARY KEY (id),
  KEY fk_game_status_idx (status),
  CONSTRAINT fk_game_status FOREIGN KEY (status) REFERENCES game_status (id)
);

DROP TABLE IF EXISTS round;
CREATE TABLE round (
  game_id int(11) NOT NULL,
  nr int(11) NOT NULL,
  started datetime NOT NULL,
  ended datetime DEFAULT NULL,
  PRIMARY KEY (game_id,nr),
  KEY fk_round_game_idx (game_id),
  CONSTRAINT fk_round_game FOREIGN KEY (game_id) REFERENCES game (id)
);

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id varchar(100) NOT NULL,
  name varchar(256) NOT NULL,
  created datetime NOT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS player;
CREATE TABLE player (
  user_id varchar(100) NOT NULL,
  game_id int(11) NOT NULL,
  score int(11) NOT NULL DEFAULT '0',
  table_position int(11) NOT NULL,
  PRIMARY KEY (user_id,game_id),
  KEY fk_player_game_idx (game_id),
  KEY fk_player_user_idx (user_id),
  CONSTRAINT fk_player_game FOREIGN KEY (game_id) REFERENCES game (id),
  CONSTRAINT fk_player_user FOREIGN KEY (user_id) REFERENCES user (id)
);

DROP TABLE IF EXISTS action;
CREATE TABLE action (
  user_id varchar(100) NOT NULL,
  game_id int(11) NOT NULL,
  round_nr int(11) NOT NULL,
  table_position int(11) NOT NULL,
  action_type varchar(45) NOT NULL,
  action_position int(11) DEFAULT NULL,
  time datetime NOT NULL,
  action_result varchar(10) DEFAULT NULL,
  result_position int(11) DEFAULT NULL,
  PRIMARY KEY (user_id,round_nr,game_id),
  KEY fk_action_player_idx (user_id,game_id),
  KEY fk_action_round_idx (game_id,round_nr),
  KEY fk_action_type_idx (action_type),
  KEY fk_action_result_idx (action_result),
  CONSTRAINT fk_action_result FOREIGN KEY (action_result) REFERENCES action_result (id),
  CONSTRAINT fk_action_player FOREIGN KEY (user_id, game_id) REFERENCES player (user_id, game_id),
  CONSTRAINT fk_action_round FOREIGN KEY (game_id, round_nr) REFERENCES round (game_id, nr),
  CONSTRAINT fk_action_type FOREIGN KEY (action_type) REFERENCES action_type (id)
);
