INSERT INTO INDIVIDUALS(INDIVID,NAME,ADDRESS) VALUES (-2,'Martin Vulkov','Burgas');
INSERT INTO INDIVIDUALS(INDIVID,NAME,ADDRESS) VALUES (-3,'Ivaylo Vatev','Burgas');
INSERT INTO INDIVIDUALS(INDIVID,NAME,ADDRESS) VALUES (-4,'Maikal Pavlov','Sofia');
INSERT INTO INDIVIDUALS(INDIVID,NAME,ADDRESS) VALUES (-5,'Kristian Georgiev','Burgas');

INSERT INTO PAYMENT_PLANS(PLANID,INDIVID,AMOUNT) VALUES (-3,-2,9999.99);
INSERT INTO PAYMENT_PLANS(PLANID,INDIVID,AMOUNT) VALUES (-4,-2,10500);
INSERT INTO PAYMENT_PLANS(PLANID,INDIVID,AMOUNT) VALUES (-5,-2,17500.50);


INSERT INTO PAYMENT_PLANS(PLANID,INDIVID,AMOUNT) VALUES (-7,-3,100);
INSERT INTO PAYMENT_PLANS(PLANID,INDIVID,AMOUNT) VALUES (-8,-3,7777.77);
INSERT INTO PAYMENT_PLANS(PLANID,INDIVID,AMOUNT) VALUES (-9,-3,11111.11);

INSERT INTO PAYMENTS(PAYMENTID,PLANID,AMOUNT,PMTDATE) VALUES (-3,-3,2000,TO_DATE('2022/07/07'));
INSERT INTO PAYMENTS(PAYMENTID,PLANID,AMOUNT,PMTDATE) VALUES (-4,-4,1000,TO_DATE('2022/11/11'));
INSERT INTO PAYMENTS(PAYMENTID,PLANID,AMOUNT,PMTDATE) VALUES (-5,-5,15000,TO_DATE('2022/05/05'));
INSERT INTO PAYMENTS(PAYMENTID,PLANID,AMOUNT,PMTDATE) VALUES (-6,-3,7000,TO_DATE('2022/04/04'));
INSERT INTO PAYMENTS(PAYMENTID,PLANID,AMOUNT,PMTDATE) VALUES (-7,-9,11111.11,TO_DATE('2022/04/04'));