function NavVertical() {
    return (
        <nav className="w-64 shrink-0 sticky top-0 h-screen border-r border-white/10 flex flex-col">
            <div className="text-center mt-8"  >
                <p className="text-1xl" >AUTOPECAS-<span className="text-blue-400">RD</span></p>
            </div>
            <ul className="ml-4 mt-12">
                <li>Funcionarios</li>
                <li>Login</li>
            </ul>
        </nav>
    )
}

export default NavVertical