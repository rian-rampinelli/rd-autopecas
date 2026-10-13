function NavMain() {
  return (
    <nav className="flex h-16 items-center justify-between border-b border-white/10 px-4 sm:px-6 lg:h-20 lg:px-10">
      <p className="text-sm text-[#737373]">Funcionários</p>
      <div className="flex items-center gap-2.5">
        <img src={`https://ui-avatars.com/api/?name=${encodeURIComponent("Rian Barbosa")}&rounded=true&size=48&background=5190dc&color=fff`} className="size-9 rounded-full" alt="" />
        <p className="hidden text-sm sm:block">Rian Barbosa</p>
      </div>
    </nav>
  )
}

export default NavMain
