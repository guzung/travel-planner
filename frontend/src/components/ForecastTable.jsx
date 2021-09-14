/* eslint-disable jsx-a11y/anchor-is-valid */
import { Table } from "antd";
import Moment from "moment";
import { useMemo } from "react";

const ForecastTable = ({ rows = [] }) => {
  const columns = useMemo(
    () => [
      {
        title: "City Name",
        dataIndex: "cityName",
        key: "cityName",
        render: (text) => <a>{text}</a>,
      },
      {
        title: "Country Code",
        dataIndex: "countryCode",
        key: "countryCode",
      },
      {
        title: "Temperature",
        dataIndex: "temperature",
        key: "temperature",
        render: (text) => <a>{text}°C</a>,
      },
      {
        title: "Clouds",
        dataIndex: "clouds",
        key: "clouds",
        render: (text) => <a>{text}%</a>,
      },
      {
        title: "Description",
        dataIndex: "description",
        key: "description",
      },
      {
        title: "Period",
        dataIndex: "date",
        key: "date",
        render: (text) => <a>{Moment(text).format("DD MMM HH:mm")}</a>,
      },
    ],
    []
  );
  return (
    <Table
      style={{ width: "70%", padding: 30 }}
      rowKey={(record) => record.id}
      columns={columns}
      bordered
      dataSource={rows}
      pagination={{ pageSize: 50 }}
      scroll={{ y: 500 }}
    />
  );
};

export default ForecastTable;
