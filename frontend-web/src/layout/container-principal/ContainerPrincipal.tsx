import type { ReactNode } from "react"


function ContainerPrincipal({ children, className }: { children: ReactNode, className?: string }) {
    return (

        <div className={`flex  bg-[#111111] min-h-[100vh]  ${className ?? ""}`}>
          
            {children}
        </div>
    )


}

export default ContainerPrincipal