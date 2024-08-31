SELECT * FROM lszmg_navonline.kuldendoszamlak where idraw>0 and lszmgdotnet=1 and szamlakeszult>='2024-08-05';
SELECT * FROM lszmg_navonline.kuldendoszamlak where idraw>0 and (formazatlan_szlaszam='30146455' or formazatlan_szlaszam='0080162419' or formazatlan_szlaszam='0080162493' or formazatlan_szlaszam='0080162494' or formazatlan_szlaszam='0080162495');
select * from lszmg_navonline.kuldendoszamlak where idraw>0 and formazatlan_szlaszam = "0080163253";
select * from lszmg_navonline.kuldendoszamlak where idraw >= 351769 and idraw <= 351775;
SELECT * FROM lszmg_navonline.kuldendoszamlak where idraw>0 and (visszaigazolo_msg<>'READY' or invoice_status<>'DONE');
SELECT * FROM lszmg_navonline.kuldendoszamlak where idraw>0 and isNull(invoice_status);
SELECT * FROM lszmg_navonline.kuldendoszamlak where idraw>0 and szamlakeszult>='2024-08-12 00:00:00';
-- 30146455 – 2024.08.05. – rontott – adószám: 27199914-2-42
-- 10145674 – 2024.08.08. – magánszemély
-- 30146460 – 2024.08.08. – magánszemély
-- + 401412943 – 2024.08.13. - adószám: 28997942-2-03 
-- 90146667 – 2024.08.14. – rontott – adószám: 32310865-2-10
-- 00800163253 08-21