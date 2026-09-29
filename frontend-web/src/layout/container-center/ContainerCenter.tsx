import type { ReactNode } from "react"

import "./ContainerCenter.css"

function ContainerCenter({children,className}: {children:ReactNode,className?: string}) {
    return (
        <div className={`container-login ${className ?? ""}`}>
            {children}
        </div>
    )
}

export default ContainerCenter