import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CityService from "../../services/CityService";
import { ToastContainer, toast } from "react-toastify";

import "react-toastify/dist/ReactToastify.css";

function CityCreate() {
  const [name, setName] = useState("");
  const navigate = useNavigate();

  const notify = () => toast("Şehir Eklendi !");

  const multiFunction = () => {
    notify();

    handleAdd();
  };

  const onCityName = (e) => {
    console.log(e.target.value);
    setName(e.target.value);
  };

  const handleAdd = () => {
    const city = {
      name,
    };

    CityService.createCity(city)

      .then((resp) => resp.data)
      .then((resp) => {
        console.log(resp);
      })
      .catch((err) => {});

    navigate("/");
  };

  return (
    <div>
      <div className="form-floating mb3">
        <input
          type="name"
          className="form-control"
          placeholder="Sivas"
          onChange={(e) => onCityName(e)}
        />

        <label className="label-style" htmlFor="floatingInputInvalid">
          <b>
            <h5>
              <i>Şehir İsmi</i>
            </h5>
          </b>
        </label>
      </div>
      <button
        type="button"
        className="btn btn-outline-danger"
        onClick={() => {
          handleAdd();
        }}
      >
        Şehir Ekle
      </button>
      <div>
        <ToastContainer />;
      </div>
    </div>
  );
}
export default CityCreate;

// Burda en son alert işlemi yapıyordun Successful warning error gibi bunlarla uğras
