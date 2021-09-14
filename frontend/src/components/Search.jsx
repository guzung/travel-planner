import { Button, DatePicker, Input } from "antd";

import "antd/dist/antd.css";
import { useCallback, useState } from "react";
import moment from "moment";

const dateFormat = "YYYY/MM/DD";

const Search = ({ onSubmit }) => {
  const [city, setCity] = useState(null);
  const [date, setDate] = useState(moment().add(1, "days"));

  const handleSubmit = useCallback(
    () => onSubmit(city, date.format()),
    [city, date, onSubmit]
  );

  const disabledDate = useCallback((current) => {
    return (
      (current && current < moment().endOf("day")) ||
      current > moment().add(5, "days")
    );
  }, []);

  return (
    <div>
      <Input.Group compact style={{ padding: 50 }}>
        <Input
          style={{ width: "30%" }}
          value={city}
          placeholder="city..."
          onChange={(e) => setCity(e.target.value)}
        />
        <DatePicker
          style={{ width: "20%" }}
          value={date}
          format={dateFormat}
          onChange={setDate}
          disabledDate={disabledDate}
        />
        <Button
          type="primary"
          style={{ width: "10%" }}
          onClick={handleSubmit}
          disabled={!(city && date)}
        >
          submit
        </Button>
      </Input.Group>
    </div>
  );
};

export default Search;
