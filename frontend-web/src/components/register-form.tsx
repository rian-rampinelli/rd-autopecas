import { useEffect, useState } from "react"
import { cn } from "../lib/utils"
import { Button } from "./ui/button"
import { Input } from "./ui/input"
import GetRoles from "../api/Roles"
import RegisterEmployee from "../api/RegisterEmployee"

type Role = { id: number; name: string }

export function RegisterForm({ className, ...props }: React.ComponentProps<"div">) {
  const [name, setName] = useState("")
  const [cpf, setCpf] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [salary, setSalary] = useState("")
  const [cargo, setCargo] = useState("")
  const [roles, setRoles] = useState<Role[]>([])
  const [selectedRoles, setSelectedRoles] = useState<number[]>([])
  const [loadingRoles, setLoadingRoles] = useState(true)
  const [submitting, setSubmitting] = useState(false)
  const [message, setMessage] = useState("")
  const [error, setError] = useState("")

  function limpaEstados() {
    setName("")
    setCpf("")
    setEmail("")
    setPassword("")
    setSalary("")
    setCargo("")
    setSelectedRoles([])
  }

  useEffect(() => {
    async function loadRoles() {
      try {
        const data = await GetRoles()
        setRoles(data)
      } catch {
        setError("Erro ao carregar funções")
      } finally {
        setLoadingRoles(false)
      }
    }

    loadRoles()
  }, [])

  function toggleRole(id: number) {
    setSelectedRoles((current) =>
      current.includes(id)
        ? current.filter((role) => role !== id)
        : [...current, id]
    )
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault()
    setError("")
    setMessage("")
    setSubmitting(true)

    const payload = {
      userName: name,
      cpf: cpf,
      email: email,
      password: password,
      salary: Number(salary),
      cargo: cargo,
      roleIds: selectedRoles,
    }

    try {
      await RegisterEmployee(payload)
      setMessage("Funcionário cadastrado com sucesso.")
      limpaEstados()
    } catch {
      setError("Erro ao cadastrar funcionário")
    } finally {
      setSubmitting(false)
    }
  }

  return (
    <div
      className={cn(
        "mx-auto w-full max-w-7xl px-4 py-6 text-white sm:px-6 lg:px-8",
        className
      )}
      {...props}
    >
      <header className="mb-6 space-y-1">
        <h1 className="text-2xl font-semibold sm:text-3xl">
          Cadastrar funcionário
        </h1>
        <p className="text-sm text-white/65">
          Preencha os dados para cadastrar um novo funcionário.
        </p>
      </header>

      <form className="space-y-5" onSubmit={handleSubmit}>
        <section className="rounded-2xl border border-white/10 bg-brand-bg-secundary p-4 sm:p-6">
          <h2 className="mb-5 text-lg font-medium">Dados pessoais</h2>

          <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
            <label className="space-y-2 text-sm">
              Nome completo *
              <Input
                name="name"
                autoComplete="name"
                placeholder="Nome"
                value={name}
                onChange={(event) => setName(event.target.value)}
                required
              />
            </label>

            <label className="space-y-2 text-sm">
              CPF *
              <Input
                name="cpf"
                inputMode="numeric"
                placeholder="00000000000"
                value={cpf}
                onChange={(event) => setCpf(event.target.value)}
                required
              />
            </label>

            <label className="space-y-2 text-sm">
              Email *
              <Input
                name="email"
                type="email"
                autoComplete="email"
                placeholder="seuemail@gmail.com"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
              />
            </label>

            <label className="space-y-2 text-sm">
              Senha *
              <Input
                name="password"
                type="password"
                autoComplete="new-password"
                placeholder="Senha"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                required
              />
            </label>

            <label className="space-y-2 text-sm">
              Salário *
              <Input
                name="salary"
                type="number"
                min="0"
                step="0.01"
                placeholder="2500"
                className="[appearance:textfield] [&::-webkit-inner-spin-button]:appearance-none [&::-webkit-outer-spin-button]:appearance-none"
                value={salary}
                onChange={(event) => setSalary(event.target.value)}
                required
              />
            </label>

            <label className="space-y-2 text-sm sm:col-span-2 lg:col-span-1">
              Cargo *
              <Input
                name="cargo"
                placeholder="Vendedor"
                value={cargo}
                onChange={(event) => setCargo(event.target.value)}
                required
              />
            </label>
          </div>
        </section>

        <section className="rounded-2xl border border-white/10 bg-brand-bg-secundary p-4 sm:p-6">
          <h2 className="text-lg font-medium">Funções</h2>

          <p className="mb-4 mt-1 text-sm text-white/65">
            Selecione uma ou mais funções para o funcionário.
          </p>

          {loadingRoles && (
            <p className="text-sm text-white/65">
              Carregando funções...
            </p>
          )}
          
          {roles.length > 0 && (
            <div className="grid grid-cols-1 gap-3 sm:grid-cols-2 lg:grid-cols-3">
              {roles.map((role) => (
                <label
                  key={role.id}
                  className="flex cursor-pointer items-center gap-3 rounded-lg border border-white/10 p-3 text-sm hover:bg-white/5"
                >
                  <input
                    type="checkbox"
                    checked={selectedRoles.includes(role.id)}
                    onChange={() => toggleRole(role.id)}
                    className="size-4 accent-white"
                  />
                  <span>{role.name}</span>
                </label>
              ))}
            </div>
          )}
        </section>

        <div className="flex flex-col items-center gap-3">
          {error && (
            <div
              role="alert"
              className="mx-auto flex w-fit max-w-full items-center gap-3 rounded-xl border border-red-400/25 bg-red-400/10 px-4 py-3 text-sm text-red-200"
            >
              <span
                aria-hidden="true"
                className="flex size-5 shrink-0 items-center justify-center rounded-full border border-red-300/50 font-semibold"
              >
                !
              </span>
              <p>{error}</p>
            </div>
          )}

          {message && (
            <div
              role="status"
              aria-live="polite"
              className="mx-auto flex w-fit max-w-full items-center gap-3 rounded-xl border border-green-400/25 bg-green-400/10 px-4 py-3 text-sm text-green-200"
            >
              <span
                aria-hidden="true"
                className="flex size-5 shrink-0 items-center justify-center rounded-full border border-green-300/50 font-semibold"
              >
                ✓
              </span>
              <p>{message}</p>
            </div>
          )}

          <div className="flex w-full justify-end">
            <Button
              type="submit"
              disabled={submitting || loadingRoles}
              className="w-full sm:w-auto"
            >
              {submitting ? "Cadastrando..." : "Cadastrar funcionário"}
            </Button>
          </div>
        </div>
      </form>
    </div>
  )
}