
GET /candidates.top_contributors.php?key=d7d55c6207bd8aff6846ec347c74fc1a&imsp_candidate_id=11 HTTP/1.1
Host: api.followthemoney.org

GET /candidates.list.php?key=d7d55c6207bd8aff6846ec347c74fc1a HTTP/1.1
Host: api.followthemoney.org

GET /followthemoney/?page_id=98 HTTP/1.1
Host: www.pauric.net

http://api.followthemoney.org/candidates.list.php?key=d7d55c6207bd8aff6846ec347c74fc1a&state=md

-- Sunlight API
API key: 160f59b8c6ea40cca6ed1c709179d647
Get the contributors
http://transparencydata.com/api/1.0/contributions.json?apikey=160f59b8c6ea40cca6ed1c709179d647&contributor_state=md|va&recipient_ft=mikulski&cycle=2008

http://transparencydata.com/api/1.0/contributions.json?apikey=160f59b8c6ea40cca6ed1c709179d647&contributor_state=md|va&recipient_state=ma&cycle=2008

- transparencydata.com bulk data
wc -l /Users/hujol/Downloads/contributions.all.csv
40,504,851 /Users/hujol/Downloads/contributions.all.csv

-- Votesmart
Get official ID first:
http://api.votesmart.org/Candidates.getByLastname?key=1ebd4d39454987ed3d3712cacdfd9e87&lastName=kerry

then getting bills candidate voted for:
http://api.votesmart.org/Votes.getByOfficial?key=1ebd4d39454987ed3d3712cacdfd9e87&candidateId=32795


-- Issues
For a ZIP code there might be 100s of candidates returned how we get all of them? Do we get all of them?


No relationship between the contributors and the issues voted by candidates: there is a need for FTM tool!
