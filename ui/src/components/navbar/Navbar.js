import { useNavigate } from "react-router-dom";
import "../../css/navbar.css";
import AuthService from "../../services/AuthService";

function Navbar() {
  const navigate = useNavigate();

  const onClick = () => {
    localStorage.removeItem("tc");
    AuthService.setToken(null);
    navigate(0);
  };

  return (
    <div>
      <div className="body_font">
        <div className="header_font">
          <h2 className="logo">LOGO</h2>
          <nav className="navigation">
            <a href="/api/hospitals">Hastaneler </a>
            <a href="#">Doktorlar </a>
            <a href="#">İletişim </a>
            {localStorage.getItem("tc") == null ? (
              <button
                onClick={(e) => navigate("/api/login")}
                className="btnLogin-popup"
              >
                Giriş Yap
              </button>
            ) : (
              <div>
                <button onClick={onClick} className="btnLogin-popup">
                  Çıkış
                </button>
                <button
                  className="btnLogin-popup"
                  onClick={(e) => navigate("/api/profile")}
                >
                  Profil
                </button>
              </div>
            )}
            {/* <button
            
              onClick={(e) => navigate("/api/login")}
              className="btnLogin-popup"
            >
              Giriş Yap
            </button> */}
          </nav>
        </div>

        {/* Login ekranı */}
      </div>
    </div>
  );
}

export default Navbar;
