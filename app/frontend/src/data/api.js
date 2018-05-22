import axios from 'axios';

const instance = axios.create({
  baseURL: process.env.API_URL,
  timeout: 1500,
  headers: {}
});

export default instance;