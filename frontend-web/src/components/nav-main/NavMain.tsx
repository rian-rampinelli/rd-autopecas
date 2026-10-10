
function NavMain() {
    return (
        <nav className=" h-24 flex items-center justify-between border-b-1 border-white/10 px-10">
            <p className="text-[#737373]" >Funcionarios</p>
            <div className="flex items-center gap-2.5">
                <img src={`https://ui-avatars.com/api/?name=${encodeURIComponent("Rian Barbosa")}&rounded=true&size=48&background=5190dc&color=fff`} className="" alt="" />

                
                <p className=""> Rian Barbosa</p>
            </div>
        </nav>
    )
}

export default NavMain