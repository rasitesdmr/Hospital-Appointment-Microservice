import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../../css/navbar.css";
import AuthService from "../../services/AuthService";
import jwt_decode from "jwt-decode";
import PropagateLoader from "react-spinners/PropagateLoader";

// console.log(typeof identityNumber);
// console.log(decode);
// console.log(localStorage.getItem("tc"));
function NavbarLogin() {
  const [identityNumber, setIdentityNumber] = useState("");
  const [password, setPassword] = useState("");
  const [isLoaded, setIsLoaded] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    setIsLoaded(true);
    setTimeout(() => {
      setIsLoaded(false);
    }, 2000);
  }, []);

  const handleTcChange = (e) => {
    const onlyNumbers = e.target.value.replace(/[^0-9]/g, "").slice(0, 11);
    setIdentityNumber(onlyNumbers);
  };

  const handlePassword = (e) => {
    const onlyPassword = e.target.value;
    setPassword(onlyPassword);
  };

  const handleLogin = () => {
    const user = {
      identityNumber,
      password,
    };
    AuthService.loginUser(user)
      .then((resp) => resp.data)
      .then((resp) => {
        // console.log(resp.token);
        var token = resp.token;
        AuthService.setToken(token);
        var decode = jwt_decode(token);
        var idNumber = decode.sub.split(",")[3];
        localStorage.setItem("tc", idNumber);
        localStorage.setItem("token", decode);
        navigate("/api/selection");

        console.log(token);
      })
      .catch((err) => alert(err.message, "Geçersiz"));
    navigate("/");
  };

  return (
    <div className="body_font">
      {isLoaded ? (
        <PropagateLoader
          color={"#007aff"}
          loading={isLoaded}
          size={15}
          aria-label="Loading Spinner"
          data-testid="loader"
        />
      ) : (
        <>
          <h2 onClick={(e) => navigate("/")} className="header_font">
            Logo
          </h2>
          <div className="wrapper">
            <span className="icon-close">
              <div onClick={(e) => navigate("/")}>
                <ion-icon name="close"></ion-icon>
              </div>
            </span>

            <div className="form-box login">
              <h2>Giriş</h2>
              <form action="#">
                <div className="input-box">
                  <span className="icon">
                    <ion-icon name="person-add"></ion-icon>
                  </span>
                  <input
                    type="text"
                    id="tc"
                    name="tc"
                    value={identityNumber}
                    onChange={(e) => handleTcChange(e)}
                    required
                  />

                  <label>TC</label>
                </div>
                <div className="input-box">
                  <span className="icon">
                    <ion-icon name="lock-closed"></ion-icon>
                  </span>
                  <input
                    onChange={(e) => handlePassword(e)}
                    type="password"
                    required
                  />

                  <label>Şifre</label>
                </div>
                <div className="remember-forgot">
                  <label>
                    <input type="checkbox" />
                    Beni Hatırla
                  </label>
                  <a href="#">Parolamı unuttum</a>
                </div>
                <button
                  onClick={(e) => handleLogin()}
                  type="submit"
                  className="btn"
                >
                  Giriş
                </button>
                <div className="login-register">
                  <p>
                    Yeni Hesap Oluştur
                    <Link to="/api/register" style={{ float: "right" }}>
                      Hesap
                    </Link>
                  </p>
                </div>
              </form>
            </div>
          </div>
        </>
      )}
    </div>
  );
}
export default NavbarLogin;
