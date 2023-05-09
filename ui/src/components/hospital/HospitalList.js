import React from "react";
import image1 from "../../images/gelecekHastane1.jpg";
import image2 from "../../images/gelecekHastane2.jpg";
import image3 from "../../images/gelecekHastane3.jpg";
import "../../css/card.css";
import { useNavigate } from "react-router-dom";

function HospitalList() {
  const navigate = useNavigate();
  return (
    <div>
      <div className="body_fontCard">
        <h2 onClick={(e) => navigate("/")} className="header_font">
          LOGO
        </h2>
        <div className="container">
          <div className="card">
            <img src={image1} alt="Hostpital1" />
            <div className="intro">
              <h1>Numune</h1>
              <div className="p_font">
                <strong>
                  <span>Numune Hastanesi</span>
                  "Lorem, ipsum dolor sit amet consectetur adipisicing elit.
                  Quae, tempore cumque "
                </strong>
              </div>
            </div>
          </div>
          <div className="card">
            <img src={image2} alt="Hostpital1" />
            <div className="intro">
              <h1>Medicana Sivas</h1>
              <div className="p_font">
                <strong>
                  <span>Medicana Hastanesi</span>
                  "Lorem, ipsum dolor sit amet consectetur adipisicing elit.
                  Quae, tempore cumque "
                </strong>
              </div>
            </div>
          </div>
          <div className="card">
            <img src={image3} alt="Hostpital1" />
            <div className="intro">
              <h1>Medicana Ankara</h1>
              <div className="p_font">
                <strong>
                  <span>Medicana international</span>
                  "Lorem, ipsum dolor sit amet consectetur adipisicing elit.
                  Quae, tempore cumque "
                </strong>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default HospitalList;
