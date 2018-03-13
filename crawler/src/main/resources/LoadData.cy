using periodic commit 500 load CSV with headers
from 'https://download.data.public.lu/resources/adresses-georeferencees-bd-adresses/20180312-053240/addresses.csv' as l fieldterminator ';'
create (a:Adresses_LU) set a=l;

match (a:Adresses_LU)
where a.code_postal starts with '8'
MERGE (cp:CodePostal_LU{code_postal:a.code_postal})
MERGE (cp)<-[:CP]-(a)

match (a:Adresses_LU)--(cp:CodePostal_LU)
merge (l:Localite_LU{localite:a.localite})
merge (l)<-[:LOCALITE]-(cp)

using periodic commit 500 load CSV with headers
from 'https://download.data.public.lu/resources/population-par-code-postal-population-per-postal-code/20180102-075950/rnrpp_code_postal.csv' as l
create (h:Habitants_LU)
set
h.code_postal=l.CODE_POSTAL,
h.habitants=toInteger(l.NOMBRE_HABITANTS);

using periodic commit 500 load CSV with headers
from 'https://download.data.public.lu/resources/matrice-des-distances-sur-routes-nationales-et-cr/20170407-205044/distances_shortest.csv' as l
create (d:DistanceRoute_LU)
set d=l,
d.straight=toFloat(l.straight),
d.length=toFloat(l.length)

using periodic commit 500 load CSV with headers
from 'https://download.data.public.lu/resources/liste-et-emplacements-des-services-proposes-par-post-luxembourg/20161124-154634/POST_Luxembourg_Services_List.csv' as l fieldterminator ';'
create (p:PostServices_LU)
set p=l;

--Need sanitize--
using periodic commit 500 load CSV with headers
from 'https://download.data.public.lu/resources/adresses-et-heures-de-releve-des-boites-aux-lettres-post-luxembourg/20161201-140601/Letter_Box.csv' as l fieldterminator ';'
create (p:ReleveBoitesLettres_LU)
set p=l;


