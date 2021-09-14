import { get } from "../helpers/api";

const BASE_URI = "http://localhost:9080/weather/api/v1";

export const getWeather = async (city, timestamp) =>
  get(`${BASE_URI}?city=${city}&timestamp=${timestamp}`);
