import React, { useEffect, useState } from "react";
import "../../css/navbar.css";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../../services/AuthService";
import Swal from "sweetalert2";

function NavbarRegister() {
  const [identityNumber, setIdentityNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleTcChange = (e) => {
    const onlyNumbers = e.target.value.replace(/[^0-9]/g, "").slice(0, 11);
    setIdentityNumber(onlyNumbers);
  };

  const handleNameChange = (e) => {
    setFirstName(e.target.value);
  };

  const handleLastNameChange = (e) => {
    setLastName(e.target.value);
  };

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleRegister = () => {
    const user = {
      identityNumber,
      firstName,
      lastName,
      email,
      password,
    };

    AuthService.registerUser(user)
      .then((resp) => resp.data)
      .then((resp) => {
        console.log(resp.data);
      })
      .catch((error) => {
        console.error(error);

        Swal.fire({
          position: "top-end",
          icon: "success",
          title: "Your work has been saved",
          showConfirmButton: false,
          timer: 3000,
        });
      });
  };

  return (
    <div className="body_font">
      <h2 onClick={(e) => navigate("/")} className="header_font">
        LOGO
      </h2>
      <div className="wrapper" style={{ height: "650px" }}>
        <span className="icon-close">
          <div onClick={(e) => navigate("/")}>
            <ion-icon name="close"></ion-icon>
          </div>
        </span>
        <div className="form-box register">
          <h2>Yeni Hesap</h2>
          <form action="#">
            <div className="input-box">
              <span className="icon">
                <ion-icon name="person"></ion-icon>
              </span>
              <input
                onChange={(e) => handleNameChange(e)}
                type="text"
                required
              />

              <label>İsim</label>
            </div>
            <div className="input-box">
              <span className="icon">
                <ion-icon name="person"></ion-icon>
              </span>
              <input
                onChange={(e) => handleLastNameChange(e)}
                type="text"
                required
              />

              <label>Soyisim</label>
            </div>

            <div className="input-box">
              <span className="icon">
                <ion-icon name="person-add"></ion-icon>
              </span>
              <input
                type="text"
                id="tc"
                name="tc"
                value={identityNumber}
                onChange={handleTcChange}
                required
              />

              <label>TC</label>
            </div>
            <div className="input-box">
              <span className="icon">
                <ion-icon name="mail"></ion-icon>
              </span>
              <input
                onChange={(e) => handleEmailChange(e)}
                type="mail"
                required
              />

              <label>Email</label>
            </div>
            <div className="input-box">
              <span className="icon">
                <ion-icon name="lock-closed"></ion-icon>
              </span>
              <input
                onChange={(e) => handlePasswordChange(e)}
                type="password"
                required
              />

              <label>Şifre</label>
            </div>
            <div className="remember-forgot">
              <label>
                <input type="checkbox" />
                Okudum onayladım
              </label>
            </div>
            <button
              onClick={(e) => handleRegister()}
              type="submit"
              className="btn"
            >
              Kayıt
            </button>

            <div className="login-register">
              <p>
                Hesabım Var
                <Link to="/api/login" style={{ float: "right" }}>
                  Giriş Yap
                </Link>
              </p>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}

export default NavbarRegister;
