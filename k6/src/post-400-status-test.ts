import { sleep, check } from 'k6';
import { Options } from 'k6/options';

/* @ts-ignore */
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.1.0/index.js';
import http from 'k6/http';

export let options:Options = {
  vus: 50,
  duration: '10s'
};

export default () => {
  const res = http.post(`http://192.168.1.16:8080/pestoapp/api/fruit`);
  check(res, {
    'status is 400': () => res.status === 400,
  });
  sleep(randomIntBetween(1,5));
};
