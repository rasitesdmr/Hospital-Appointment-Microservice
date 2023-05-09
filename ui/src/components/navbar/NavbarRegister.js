import React, { useState } from "react";
import "../../css/navbar.css";
import { Link, useNavigate } from "react-router-dom";

function NavbarRegister() {
  const [tc, setTc] = useState("");
  const navigate = useNavigate();

  const handleTcChange = (e) => {
    const onlyNumbers = e.target.value.replace(/[^0-9]/g, "").slice(0, 11);
    setTc(onlyNumbers);
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
              <input type="text" required />

              <label>İsim</label>
            </div>
            <div className="input-box">
              <span className="icon">
                <ion-icon name="person"></ion-icon>
              </span>
              <input type="text" required />

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
                value={tc}
                onChange={handleTcChange}
                required
              />

              <label>TC</label>
            </div>
            <div className="input-box">
              <span className="icon">
                <ion-icon name="mail"></ion-icon>
              </span>
              <input type="email" required />

              <label>Email</label>
            </div>
            <div className="input-box">
              <span className="icon">
                <ion-icon name="lock-closed"></ion-icon>
              </span>
              <input type="password" required />

              <label>Şifre</label>
            </div>
            <div className="remember-forgot">
              <label>
                <input type="checkbox" />
                Okudum onayladım
              </label>
            </div>
            <button type="submit" className="btn">
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
