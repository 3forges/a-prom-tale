import http from 'k6/http';
import { check } from 'k6';

export default function () {
    const NOM_D_UN_FRUIT1 = `lanzones`
    const DESIRED_LAG_IN_MILLISECONDS1 = `30`
    
    const NOM_D_UN_FRUIT2 = `lanzones`
    const DESIRED_LAG_IN_MILLISECONDS2 = `300`
    
    const NOM_D_UN_FRUIT3 = `lanzones`
    const DESIRED_LAG_IN_MILLISECONDS3 = `15`
    

  let responses = http.batch([
    ['GET', `http://192.168.1.16:8080/pestoapp/api/fruit/${NOM_D_UN_FRUIT1}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS1}`, null, { tags: { ctype: 'json' } }],
    ['GET', `http://192.168.1.16:8080/pestoapp/api/fruit/${NOM_D_UN_FRUIT2}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS2}`, null, { tags: { ctype: 'json' } }],
    ['GET', `http://192.168.1.16:8080/pestoapp/api/fruit/${NOM_D_UN_FRUIT3}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS3}`, null, { tags: { ctype: 'json' } }],
    ['GET', `http://192.168.1.16:8080/pestoapp/api/fruit`, null, { tags: { ctype: 'json' } }],
    ['GET', `http://192.168.1.16:8080/pestoapp/api/fruit`, null, { tags: { ctype: 'json' } }],
    ['GET', `http://192.168.1.16:8080/pestoapp/api/fruit`, null, { tags: { ctype: 'json' } }],
  ]);
  check(responses[0], {
    'main page status was 200': (res) => res.status === 200,
  });
}
