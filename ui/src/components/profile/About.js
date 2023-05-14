import React, { useState } from "react";
import doctor from "../../images/doctor-photo.png";
import "../../css/profile.css";
import { useNavigate } from "react-router-dom";

const About = () => {
  const navigate = useNavigate();
  const [randevular, setRandevular] = useState([
    { id: 1, isim: "Göz Polikinliği", tarih: "11.05.2023" },
    { id: 2, isim: "Diş Polikinliği", tarih: "12.05.2023" },
    { id: 3, isim: "Kulak Burun Boğaz Polikinliği", tarih: "13.05.2023" },
  ]);

  const handleRandevuSil = (id) => {
    if (window.confirm("Bu randevuyu silmek istediğinize emin misiniz?")) {
      setRandevular(randevular.filter((randevu) => randevu.id !== id));
    }
  };

  const tc = localStorage.getItem("tc");
  const isim = "Burhan";
  const soyad = "Kavak";
  const email = "burhan.kvk58@gmail.com";
  const randevu = "Göz Polikinliği";
  const tarih = "11.05.2023";
  return (
    <div className="body__font">
      <div className="header__wrapper">
        <header></header>.
        <div className="cols__container">
          <div className="left__col">
            <div className="img__container">
              <img src={doctor} alt="doctor" />
              <span></span>
            </div>
            <h2>Profil sayfası</h2>
            <p>TC : {tc}</p>
            <p>İsim : {isim}</p>
            <p>Soyad : {soyad}</p>
            <p>Email : {email}</p>
            <ul className="about">
              {randevular.map((randevu) => (
                <li key={randevu.id}>
                  <span>{randevu.isim}</span>
                  {randevu.tarih}
                  <button onClick={() => handleRandevuSil(randevu.id)}>
                    Randevu Sil
                  </button>
                </li>
              ))}
            </ul>
            <div className="content">
              <p>
                Structural (Yapısal) Tasarım Desenleri, nesne ve sınıf
                yapılarını organize etmek ve birleştirmek için kullanılan
                tasarım desenleridir. Bu desenler, nesne ve sınıflar arasındaki
                ilişkileri belirlemeye yardımcı olur ve programın yapısını
                kolaylaştırır.
              </p>
            </div>
          </div>

          <div className="right__col">
            <nav>
              <ul>
                <a href="/">Anasayfa</a>
              </ul>
              <button onClick={(e) => navigate("/api/selection")}>
                Yeni Randevu Oluştur
              </button>
            </nav>
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;