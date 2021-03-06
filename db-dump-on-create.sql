;              
CREATE USER IF NOT EXISTS ROOT SALT '60f4f0edd1b8bd30' HASH '4b01ae4186188237ae4621c8a1f9c3b7aa754c545c49e6780f455c12233cbfa1' ADMIN;          
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_04D1E5A0_F870_410D_A0CB_3B8A9C9F96F0 START WITH 1 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_61C37590_A9C1_4FCD_89A8_1A44215230A6 START WITH 1 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_21D6DCD3_FCA3_423C_AFFC_B7D9F82272EC START WITH 1 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_140C9BE2_7A86_43EF_B1F1_CDF3D2BDFCD4 START WITH 1 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_F04C2072_6A53_4A58_B301_109D28D38641 START WITH 1 BELONGS_TO_TABLE;     
CREATE SEQUENCE PUBLIC.SYSTEM_SEQUENCE_9A48F1C4_8999_404E_B4FB_192C739D0CEE START WITH 1 BELONGS_TO_TABLE;     
CREATE CACHED TABLE PUBLIC.ANSWER(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_140C9BE2_7A86_43EF_B1F1_CDF3D2BDFCD4) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_140C9BE2_7A86_43EF_B1F1_CDF3D2BDFCD4,
    IS_CORRECT BOOLEAN,
    TITLE VARCHAR(2048) NOT NULL,
    QUESTION_ID BIGINT NOT NULL
);              
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.CONSTRAINT_7 PRIMARY KEY(ID);  
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ANSWER;   
CREATE CACHED TABLE PUBLIC.PASSWORD(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_9A48F1C4_8999_404E_B4FB_192C739D0CEE) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_9A48F1C4_8999_404E_B4FB_192C739D0CEE,
    PASSMD5 VARCHAR(2048),
    SECTION VARCHAR(255)
);  
ALTER TABLE PUBLIC.PASSWORD ADD CONSTRAINT PUBLIC.CONSTRAINT_77 PRIMARY KEY(ID);               
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.PASSWORD; 
CREATE CACHED TABLE PUBLIC.QUESTION(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_21D6DCD3_FCA3_423C_AFFC_B7D9F82272EC) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_21D6DCD3_FCA3_423C_AFFC_B7D9F82272EC,
    TITLE VARCHAR(2048) NOT NULL,
    SPECIFICATION_ID BIGINT NOT NULL
);               
ALTER TABLE PUBLIC.QUESTION ADD CONSTRAINT PUBLIC.CONSTRAINT_E PRIMARY KEY(ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.QUESTION; 
CREATE CACHED TABLE PUBLIC.QUESTION_LEVEL(
    QUESTION_ID BIGINT NOT NULL,
    LEVELS VARCHAR(255)
);         
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.QUESTION_LEVEL;           
CREATE CACHED TABLE PUBLIC.SPECIFICATION(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_04D1E5A0_F870_410D_A0CB_3B8A9C9F96F0) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_04D1E5A0_F870_410D_A0CB_3B8A9C9F96F0,
    NAME VARCHAR(2048) NOT NULL
); 
ALTER TABLE PUBLIC.SPECIFICATION ADD CONSTRAINT PUBLIC.CONSTRAINT_7A PRIMARY KEY(ID);          
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.SPECIFICATION;            
CREATE CACHED TABLE PUBLIC.TEST(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_61C37590_A9C1_4FCD_89A8_1A44215230A6) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_61C37590_A9C1_4FCD_89A8_1A44215230A6,
    CORRECT_ANSWERS INTEGER,
    DATE TIMESTAMP,
    LEVEL VARCHAR(255),
    RESULT VARCHAR(255),
    SCORE INTEGER,
    TESTING_TIME INTEGER,
    TOTAL_QUESTIONS INTEGER,
    SPECIFICATION_ID BIGINT,
    USER_ID BIGINT NOT NULL
);     
ALTER TABLE PUBLIC.TEST ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(ID);    
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.TEST;     
CREATE CACHED TABLE PUBLIC.USR(
    ID BIGINT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_F04C2072_6A53_4A58_B301_109D28D38641) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_F04C2072_6A53_4A58_B301_109D28D38641,
    NAME VARCHAR(255),
    SECOND_NAME VARCHAR(255),
    SURNAME VARCHAR(255)
);             
ALTER TABLE PUBLIC.USR ADD CONSTRAINT PUBLIC.CONSTRAINT_1 PRIMARY KEY(ID);     
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.USR;      
ALTER TABLE PUBLIC.PASSWORD ADD CONSTRAINT PUBLIC.UK_PQVXY03858YAUNRC0RE0T5EUU UNIQUE(SECTION);
ALTER TABLE PUBLIC.SPECIFICATION ADD CONSTRAINT PUBLIC.UK_BDPR10AXWX0A7OGP5AX531N9F UNIQUE(NAME);              
ALTER TABLE PUBLIC.QUESTION_LEVEL ADD CONSTRAINT PUBLIC.FKBOTSMRMKDG7D0W20BQXU9H5P3 FOREIGN KEY(QUESTION_ID) REFERENCES PUBLIC.QUESTION(ID) NOCHECK;           
ALTER TABLE PUBLIC.TEST ADD CONSTRAINT PUBLIC.FK6CW4B4DL3SBJKM9QBWVA403EJ FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USR(ID) NOCHECK;              
ALTER TABLE PUBLIC.ANSWER ADD CONSTRAINT PUBLIC.FK8FRR4BCABMMEYYU60QT7IIBLO FOREIGN KEY(QUESTION_ID) REFERENCES PUBLIC.QUESTION(ID) NOCHECK;   
ALTER TABLE PUBLIC.QUESTION ADD CONSTRAINT PUBLIC.FK9K9VMWU2MTXXIFWQEIPBELYC3 FOREIGN KEY(SPECIFICATION_ID) REFERENCES PUBLIC.SPECIFICATION(ID) NOCHECK;       
ALTER TABLE PUBLIC.TEST ADD CONSTRAINT PUBLIC.FKNM4QQMOX4OW756EABEBJG30A7 FOREIGN KEY(SPECIFICATION_ID) REFERENCES PUBLIC.SPECIFICATION(ID) NOCHECK;           
