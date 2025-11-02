import { sleep, check } from 'k6';
import { Options } from 'k6/options';
import http from 'k6/http';

export let options:Options = {
  vus: 50,
  duration: '10s'
};

export default () => {
  // const res = http.get('https://test-api.k6.io');
  const NOM_D_UN_FRUIT = `lanzones`
  const DESIRED_LAG_IN_MILLISECONDS = `300`
  
  const res = http.get(`http://192.168.1.16:8080/pestoapp/api/fruit/${NOM_D_UN_FRUIT}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS}`);
  check(res, {
    'status is 200': () => res.status === 200,
  });
  sleep(1);
};
