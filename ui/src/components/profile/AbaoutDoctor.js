import React, { useEffect } from "react";
import "../../css/doctorProfile.css";
function AbaoutDoctor() {
  useEffect(() => {
    const handleResize = () => {
      const scrollWidth =
        document.querySelector(".tbl-content").offsetWidth -
        document.querySelector(".tbl-content table").offsetWidth;
      document.querySelector(
        ".tbl-header"
      ).style.paddingRight = `${scrollWidth}px`;
    };

    window.addEventListener("load", handleResize);
    window.addEventListener("resize", handleResize);

    // Komponent çözüldüğünde event listener'ları kaldır
    return () => {
      window.removeEventListener("load", handleResize);
      window.removeEventListener("resize", handleResize);
    };
  }, []);
  return (
    <section>
      <div className="arrow">
        <span></span>
        <span></span>
        <span></span>
      </div>
      <h1>Randevular</h1>
      <div className="tbl-header">
        <table cellPadding="0" cellSpacing="0">
          <thead>
            <tr>
              <th>Hasta TC Kimlik Numarası</th>

              <th>Ad</th>
              <th>Soyad</th>
              <th>Randevu Tarihi</th>
              <th>Randevu Saati</th>
              <th>Durum</th>
            </tr>
          </thead>
        </table>
      </div>
      <div className="tbl-content">
        <table cellPadding="0" cellSpacing="0">
          <tbody>
            <tr>
              <td>12345678910</td>
              <td>Ayşe</td>
              <td>Akbaba</td>
              <td>17.15.2023</td>
              <td>13.45</td>
              <td>
                <select className="select">
                  <option value="Aktif">Aktif</option>
                  <option value="Geldi">Geldi</option>
                  <option value="Gelmedi">Gelmedi</option>
                </select>
              </td>
            </tr>
            <tr>
              <td>01987654321</td>
              <td>Daren</td>
              <td>Hacbekri</td>
              <td>17.15.2023</td>
              <td>14.00</td>
              <td>
                <select className="select">
                  <option value="Aktif">Aktif</option>
                  <option value="Geldi">Geldi</option>
                  <option value="Gelmedi">Gelmedi</option>
                </select>
              </td>
            </tr>
            <tr>
              <td>75315986248</td>
              <td>Burhan</td>
              <td>Kavak</td>
              <td>17.15.2023</td>
              <td>14.15</td>
              <td>
                <select className="select">
                  <option value="Aktif">Aktif</option>
                  <option value="Geldi">Geldi</option>
                  <option value="Gelmedi">Gelmedi</option>
                </select>
              </td>
            </tr>
            <tr>
              <td>31798246531</td>
              <td>Muhammed Raşit</td>
              <td>Eşdemir</td>
              <td>17.15.2023</td>
              <td>14.30</td>
              <td>
                <select className="select">
                  <option value="Aktif">Aktif</option>
                  <option value="Geldi">Geldi</option>
                  <option value="Gelmedi">Gelmedi</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>13972864513</td>
              <td>Yusuf</td>
              <td>Elmas</td>
              <td>17.15.2023</td>
              <td>14.45</td>
              <td>
                <select className="select">
                  <option value="Aktif">Aktif</option>
                  <option value="Geldi">Geldi</option>
                  <option value="Gelmedi">Gelmedi</option>
                </select>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <button className="button_doctor">
        <span>Button</span>
        <i></i>
      </button>
    </section>
  );
}

export default AbaoutDoctor;
