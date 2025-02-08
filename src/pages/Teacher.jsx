"use client"
import { useState, useEffect } from "react"
import { DataTable } from "@/components/teacher/data-table"
import { SidebarProvider, SidebarInset } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
// Add Checkbox import at the top
import { Checkbox } from "@/components/ui/checkbox"
import axios from "axios"

const studentColumns = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={table.getIsAllPageRowsSelected()}
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "id",
    header: "ID",
  },
  {
    accessorKey: "fullName",
    header: "Name",
    cell: ({ row }) => `${row.original.prenom} ${row.original.nom}`,
  },
  {
    accessorKey: "niveau",
    header: "Grade",
  },
  {
    accessorKey: "classe",
    header: "Class",
  },
  {
    accessorKey: "dateInscription",
    header: "Inscription Date",
    cell: ({ row }) => new Date(row.original.dateInscription).toLocaleDateString(),
  },
]

export default function Teacher() {
  const [students, setStudents] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState("")
  const [selectedGrade, setSelectedGrade] = useState("")
  const [selectedClass, setSelectedClass] = useState("")

  useEffect(() => {
    fetchStudents()
  }, [])

  const fetchStudents = async () => {
    try {
      setLoading(true)
      const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/eleves`)
      setStudents(response.data)
    } catch (error) {
      console.error("Error fetching students:", error)
      setError("Failed to load students")
    } finally {
      setLoading(false)
    }
  }

  // Get unique grades and classes from the data
  const grades = [...new Set(students.map(student => student.niveau))].sort()
  const classes = [...new Set(students.map(student => student.classe))].sort()

  const filteredData = students.filter(student => {
    const matchesGrade = !selectedGrade || selectedGrade === "all" || student.niveau === selectedGrade
    const matchesClass = !selectedClass || selectedClass === "all" || student.classe === selectedClass
    return matchesGrade && matchesClass
  })

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
                  <h1 className="text-2xl font-semibold">Students</h1>
                  <p className="text-muted-foreground">
                    View and filter students by grade and class
                  </p>
                  {error && <p className="text-sm text-red-500 mt-2">{error}</p>}
                </div>
                <div className="flex gap-4">
                  <Select value={selectedGrade} onValueChange={setSelectedGrade}>
                    <SelectTrigger className="w-[180px]">
                      <SelectValue placeholder="Select grade" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Grades</SelectItem>
                      {grades.map(grade => (
                        <SelectItem key={grade} value={grade}>
                          {grade}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <Select value={selectedClass} onValueChange={setSelectedClass}>
                    <SelectTrigger className="w-[180px]">
                      <SelectValue placeholder="Select class" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Classes</SelectItem>
                      {classes.map(cls => (
                        <SelectItem key={cls} value={cls}>
                          {cls}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <DataTable 
                columns={studentColumns} 
                data={filteredData}
                loading={loading}
              />
            </div>
          </div>
        </SidebarInset>
      </div>
    </SidebarProvider>
  )
}

