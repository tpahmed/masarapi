"use client"

import { columns } from "@/components/rapport/columns"
import { DataTable } from "@/components/rapport/data-table"
import { SidebarProvider, SidebarInset } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { AddRapportSheet } from "@/components/rapport/add-rapport-sheet"
import { useState, useEffect } from "react"
import axios from "axios"

export default function Tasks() {
  const [reports, setReports] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchReports()
  }, [])

  const fetchReports = async () => {
    try {
      setLoading(true)
      const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/rapports`)
      const formattedReports = response.data.map(report => ({
        id: report.id,
        idRapport: report.idRapport,
        generatedBy: report.generePar,
        Date: new Date(report.date).toISOString().split('T')[0],
        Type: report.typeRapport,
        Content: report.contenu
      }))
      setReports(formattedReports)
    } catch (error) {
      console.error("Error fetching reports:", error)
    } finally {
      setLoading(false)
    }
  }

  return (
    <SidebarProvider>
      <div className="flex min-h-screen dark:bg-background">
        <AppSidebar className="border-r border-gray-300 shadow-[2px_0_10px_rgb(107,114,128)]" />
        <SidebarInset className="flex-1">
          <div className="flex flex-col bg-gray-200 h-full">
            <Header />
            <div className="p-4">
              <div className="flex items-center justify-between mb-6">
                <div>
                  <h1 className="text-2xl font-semibold">Rapport</h1>
                  <p className="text-muted-foreground">
                   { "Here's a list of your Rapport!"}
                  </p>
                </div>
                <div className="flex gap-2">
                  <AddRapportSheet onSuccess={fetchReports} />
                </div>
              </div>

              <DataTable 
                columns={columns} 
                data={reports} 
                loading={loading}
              />
            </div>
          </div>
        </SidebarInset>
      </div>
    </SidebarProvider>
  )
}

