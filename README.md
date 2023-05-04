# Payments
 
Задача
Направете таблица individual с колони
indivId – primary key varchar
name – varchar, собствено име
address – address of the person, varchar

Направете таблица  payment_plans
planId – primary key, identifier of the payment plan, varchar
indivId – reference to indivId from individual, the individual who owns money
amount – the whole amount which should be paid, number
	
Направете таблица payments  
paymentId – primary key, id of the payment, varchar
planId – reference to payment plan on which the payment is made
amount – the amount which is paid, number
payment_date_dt   - the date on which the payment is made, datetime with timezone

Напишете Rest услуги, с които да се въвеждат, показват, променят individuals, payment_plans и payments.
Напишете Rest услуга, която да показва всички плащания по даден разплащателен план за даден човек.
Напишете Rest услуга, която да показва всички планове със сумата оставаща да се плати за тях от даден човек. Добавете търсене и сортиране по колони.
Разработете тестове за услугите.
Използвайте Spring, NamedParameterJdbcOperations. Използвайте модела @RestController- >@Service -> @Repository. 

