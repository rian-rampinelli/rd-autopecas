import { cn } from "../lib/utils"
import { Button } from "./ui/button"
import {Card,CardContent,CardDescription,CardHeader,CardTitle,} from "./ui/card"
import {Field,FieldDescription,FieldGroup,FieldLabel} from "./ui/field"
import { Input } from "./ui/input"
import { Link } from "react-router-dom";

export function RegisterForm({className,...props}: React.ComponentProps<"div">) {
  return (
    <div className={ cn("flex flex-col gap-6", className)} {...props}>
      <Card>
        <CardHeader>
          <CardTitle>Crie sua conta</CardTitle>
          <CardDescription>
            Digite seu dados de cadastro
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input
                  id="email"
                  type="email"
                  placeholder="seunome@example.com"
                  required
                />
              </Field>
              <Field>
                <div className="flex items-center">
                  <FieldLabel htmlFor="password">Password</FieldLabel>
                </div>
                <Input placeholder="************" id="password" type="password" required />
              </Field>
              <Field>
                <div className="flex items-center">
                  <FieldLabel htmlFor="password">Confirme PassWord</FieldLabel>
                </div>
                <Input placeholder="************" id="password" type="password" required />
              </Field>
              <Field>
                <Button type="submit">Criar</Button>
                <FieldDescription className="text-center">
                  Ja possui conta? <Link className=":hover text-blue-400" to={"/login"}>Entre</Link>
                </FieldDescription>
              </Field>
            </FieldGroup>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
