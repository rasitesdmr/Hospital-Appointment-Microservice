import React from "react";
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { useEffect } from "react";
import CityService from "../../services/CityService";

export default function CityList() {
  const [cities, setCities] = useState([]);

  useEffect(() => {
    CityService.getCityList()
      .then((resp) => setCities(resp.data))
      .catch((error) => {
        console.log(error.message);
      });
  }, []);

  return (
    <div>
      <table className="table table-dark table-striped">
        <thead>
          <tr>
            <th>City Id</th>
            <th>City Name</th>
          </tr>
        </thead>
        <tbody>
          {(cities ?? []).map((city) => (
            <tr key={city.id}>
              <td>{city.id}</td>
              <td>{city.name}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
