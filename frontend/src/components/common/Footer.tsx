import React from "react";

const images = {
  fb_logo: require("../../assets/images/facebook (1).png"),
  ins_logo: require("../../assets/images/instagram.png"),
  tiktok_logo: require("../../assets/images/tik-tok.png"),
  yt_logo: require("../../assets/images/youtube.png"),
  twitter_logo: require("../../assets/images/twitter.png"),
  bell: require("../../assets/images/bell.png"),
  tick: require("../../assets/images/404-tick.png"),
  logo: require("../../assets/images/logo.png"),
};
function Footer() {
  return (
    <div className="w-full h-[344px] bg-[#3f444f] py-[62px] px-[120px] text-white">
      <div className="h-full flex justify-between">
        {/* Giới thiệu */}
        <div className="item-text">
          <h2 className="text-2xl font-extrabold mb-5">Giới thiệu</h2>
          <div className="text-lg font-semibold">
            <p>Project Thiết kế và xây dựng phần mềm</p>
            <p>GVHD: TS. Nguyễn Thị Thu Trang</p>
          </div>
        </div>

        {/* Sản phẩm */}
        <div className="item-text">
          <h2 className="text-2xl font-extrabold mb-5">Sản phẩm</h2>
          <div className="text-lg font-semibold">
            <p>CD</p>
            <p>DVD</p>
            <p>Book</p>
            <p>Phụ kiện</p>
          </div>
        </div>

        {/* Thành viên */}
        <div className="item-text">
          <h2 className="text-2xl font-extrabold mb-5">Thành viên</h2>
          <div className="text-lg font-semibold">
            <p>Vũ Ngọc Anh 20215313</p>
          </div>
          <div className="text-lg font-semibold">
            <p>Nguyễn Trung Chiến 20215320</p>
          </div>
        </div>

        {/* Liên hệ với chúng tôi */}
        <div className="item-contact">
          <h2 className="text-2xl font-extrabold mb-5">
            Liên hệ với chúng tôi
          </h2>
          <div className="flex items-center justify-between">
            <img className="w-10 h-10" src={images.fb_logo} alt="facebook" />
            <img className="w-10 h-10" src={images.ins_logo} alt="instagram" />
            <img className="w-10 h-10" src={images.tiktok_logo} alt="tiktok" />
            <img className="w-10 h-10" src={images.yt_logo} alt="youtube" />
            <img
              className="w-10 h-10"
              src={images.twitter_logo}
              alt="twitter"
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Footer;
