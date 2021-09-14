import { notification } from "antd";

import "antd/dist/antd.css";
import { useCallback, useState } from "react";
import ForecastTable from "./ForecastTable";
import Search from "./Search";
const WeatherForm = ({ onSubmit }) => {
  const [weatherData, setWeather] = useState([]);
  const onError = useCallback(
    () =>
      notification.error({
        message: "City data not found",
      }),
    []
  );

  const handleSubmit = useCallback(
    (...params) =>
      onSubmit(...params)
        .then((data) =>
          setWeather([
            ...data.sort((a, b) => new Date(a.date) - new Date(b.date)),
            ...weatherData,
          ])
        )
        .catch(onError),
    [weatherData, onError, onSubmit]
  );

  return (
    <div>
      <Search onSubmit={handleSubmit} />
      <ForecastTable rows={weatherData} />
    </div>
  );
};

export default WeatherForm;
