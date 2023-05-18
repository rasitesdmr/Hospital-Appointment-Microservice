import React, { useEffect, useState } from "react";
import doctor from "../../images/doctor-photo.png";
import "../../css/profile.css";
import { useNavigate } from "react-router-dom";
import Swal from "sweetalert2";
import ProfileService from "../../services/ProfileService";
import AppointmentService from "../../services/AppointmentService";

const About = () => {
  const navigate = useNavigate();
  const [randevular, setRandevular] = useState([]);
  const tc = localStorage.getItem("tc");
  const isim = localStorage.getItem("firstName");
  const soyad = localStorage.getItem("lastName");
  const email = localStorage.getItem("email");

  useEffect(() => {
    ProfileService.getAppointmentList().then((resp) => {
      console.log(resp.data);

      setRandevular(resp.data);

      // Örnek: İlk randevunun e-posta adresine erişim
      const firstAppointmentEmail = resp.data[0].email;
      console.log("İlk randevu e-posta adresi:", firstAppointmentEmail);

      // Örnek: Tüm randevu isimlerine erişim
      const appointmentNames = resp.data.map(
        (appointment) => appointment.firstName
      );
      console.log("Randevu isimleri:", appointmentNames);
    });
  }, []);

  const handleRandevuSil = (id) => {
    Swal.fire({
      title: "Emin misiniz?",
      text: "Bunu Geri Alamazsınız!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Evet, sil!",
    }).then((result) => {
      AppointmentService.deleteAppointment(id).then((resp) => {
        console.log(resp);
      });
      if (result.isConfirmed) {
        Swal.fire("Silindi!", "Randevunuz silindi", "success");
        setRandevular(randevular.filter((randevu) => randevu.id !== id));
      }
    });
  };

  return (
    <div className="body__font">
      <div className="header__wrapper">
        <header></header>
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
                  <ul>
                    {randevu.appointmentResponses.map((appointment) => (
                      <li key={appointment.id}>
                        <span>{appointment.cityResponse.name}</span>
                        <span>{appointment.hospitalResponse.name}</span>
                        <span>{appointment.clinicResponse.name}</span>
                        <span>{appointment.appointmentDate}</span>
                        <span>{appointment.appointmentTime}</span>

                        <button
                          className="button_about"
                          onClick={() => handleRandevuSil(appointment.id)}
                        >
                          Randevu Sil
                        </button>
                      </li>
                    ))}
                  </ul>
                </li>
              ))}
            </ul>

            <div className="content">
              <p>
                Merhaba! Profil sayfasına hoş geldiniz. Burada sizin randevu
                bilgilerinizi ve diğer önemli bilgileri görüntüleyebilirsiniz.
                Randevu geçmişinizi, gelecek randevularınızı ve kişisel
                bilgilerinizi gözden geçirebilir, güncelleyebilir ve
                randevularınızı yönetebilirsiniz. Sağlığınızı önemsiyor ve size
                en iyi hizmeti sunmak için buradayız. Herhangi bir sorunuz veya
                destek ihtiyacınız olduğunda bize ulaşmaktan çekinmeyin. İyi
                günler dileriz!
              </p>
            </div>
          </div>

          <div className="right__col">
            <nav>
              <ul>
                <a href="/">Anasayfa</a>
              </ul>
              <button
                className="button_about"
                onClick={(e) => navigate("/api/selection")}
              >
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
