function NavVertical() {
  return (
    <nav className="sticky top-0 z-10 flex h-auto w-full shrink-0 items-center justify-between border-b border-white/10 bg-[#111111] px-4 py-3 md:h-screen md:w-64 md:flex-col md:items-stretch md:justify-start md:border-b-0 md:border-r md:px-0">
      <div className="text-center md:mt-8">
        <p className="text-base">AUTOPECAS-<span className="text-blue-400">RD</span></p>
      </div>
      <ul className="flex items-center gap-4 text-sm md:ml-4 md:mt-12 md:flex-col md:items-start md:gap-3">
        <li>Funcionarios</li>
        <li>Login</li>
      </ul>
    </nav>
  )
}

export default NavVertical
