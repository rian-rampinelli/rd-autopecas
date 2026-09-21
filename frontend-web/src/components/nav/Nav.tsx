import { FaInstagram,FaLinkedin,FaFacebook,FaYoutube } from "react-icons/fa";

function Nav() {
    return (
        <nav className="bg-[#1a1a18] w-[100vw] fixed top-0 flex justify-center">
            <div className=" w-[1200px] p-10 flex justify-between  ">
                <div className=" ">AUTOPECAS-<span className="text-blue-400">RD</span></div>
                <div className="flex gap-3">
                <FaInstagram size={28}  className="text-white transition-transform duration-200 hover:scale-120 hover:text-blue-400">

                </FaInstagram>
                <FaLinkedin size={28}  className="text-white transition-transform duration-200 hover:scale-120 hover:text-blue-400">

                </FaLinkedin>
                <FaYoutube size={28}  className="text-white transition-transform duration-200 hover:scale-120 hover:text-blue-400">

                </FaYoutube>
                <FaFacebook size={28}  className="text-white transition-transform duration-200 hover:scale-120 hover:text-blue-400">

                </FaFacebook>

            </div>
            </div>
        </nav>
    )
}

export default Nav