import type { ReactNode } from "react"


function ContainerPrincipal({ children, className }: { children: ReactNode, className?: string }) {
    return (

        <div className={`flex min-h-screen flex-col bg-[#111111] md:flex-row ${className ?? ""}`}>
          
            {children}
        </div>
    )


}

export default ContainerPrincipal