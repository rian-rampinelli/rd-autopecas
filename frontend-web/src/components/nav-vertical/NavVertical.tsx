import { NavLink } from "react-router-dom"
import { LogIn, UsersRound } from "lucide-react"

function NavVertical() {
  const linkClass = ({ isActive }: { isActive: boolean }) =>
    `flex items-center gap-2 rounded-lg px-3 py-2.5 transition-colors hover:bg-white/5 hover:text-white ${isActive ? "bg-blue-500/10 text-blue-400" : "text-white/70"}`

  return (
    <nav aria-label="Navegação principal" className="sticky top-0 z-10 flex h-auto w-full shrink-0 flex-wrap items-center justify-between gap-3 border-b border-white/10 bg-[#111111] px-4 py-3 md:h-screen md:w-64 md:flex-nowrap md:flex-col md:items-stretch md:justify-start md:gap-0 md:border-b-0 md:border-r md:px-0">
      <div className="text-center md:mt-8">
        <p className="whitespace-nowrap text-base font-semibold">AUTOPECAS-<span className="text-blue-400">RD</span></p>
      </div>
      <ul className="flex items-center gap-2 text-sm md:ml-3 md:mr-3 md:mt-12 md:flex-col md:items-stretch">
        <li>
          <NavLink to="/register" className={linkClass}>
            <UsersRound size={18} aria-hidden="true" />
            <span>Funcionários</span>
          </NavLink>
        </li>
        <li>
          <NavLink to="/login" className={linkClass}>
            <LogIn size={18} aria-hidden="true" />
            <span>Login</span>
          </NavLink>
        </li>
      </ul>
    </nav>
  )
}

export default NavVertical
