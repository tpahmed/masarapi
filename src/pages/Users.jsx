import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { useState, useEffect } from "react"
import axios from "axios"
import { Button } from "@/components/ui/button"
import { AddStudentDialog } from "@/components/user/add-student-dialog"
import { AddTeacherDialog } from "@/components/user/add-teacher-dialog"
import { EditStudentDialog } from "@/components/user/edit-student-dialog"
import { EditTeacherDialog } from "@/components/user/edit-teacher-dialog"
import { DeleteConfirmationDialog } from "@/components/user/delete-confirmation-dialog"
import { DataTable } from "@/components/user/data-table"
import { Checkbox } from "@/components/ui/checkbox"


export default function UserManagement() {
  const [tableType, setTableType] = useState("students")
  const [isStudentDialogOpen, setIsStudentDialogOpen] = useState(false)
  const [isTeacherDialogOpen, setIsTeacherDialogOpen] = useState(false)
  const [isEditStudentDialogOpen, setIsEditStudentDialogOpen] = useState(false)
  const [isEditTeacherDialogOpen, setIsEditTeacherDialogOpen] = useState(false)
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false)
  const [editingStudent, setEditingStudent] = useState(null)
  const [editingTeacher, setEditingTeacher] = useState(null)
  const [selectedIds, setSelectedIds] = useState([])
  const [students, setStudents] = useState([])
  const [teachers, setTeachers] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    fetchData()
  }, [tableType])

  const fetchData = async () => {
    try {
      setLoading(true)
      if (tableType === "students") {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/eleves`)
        const formattedStudents = response.data.map(student => ({
          id: student.id,
          name: `${student.prenom} ${student.nom}`,
          email: `${student.prenom.toLowerCase()}.${student.nom.toLowerCase()}@school.com`,
          level: student.niveau,
          yearOfInscription: new Date(student.dateInscription).getFullYear(),
          class: student.classe,
          idEleve: student.idEleve
        }))
        setStudents(formattedStudents)
      } else {
        const response = await axios.get(`${import.meta.env.VITE_API_URL}/api/enseignants`)
        const formattedTeachers = response.data.map(teacher => ({
          id: teacher.id,
          name: `${teacher.prenom} ${teacher.nom}`,
          email: `${teacher.prenom.toLowerCase()}.${teacher.nom.toLowerCase()}@school.com`,
          subject: teacher.matiere,
          experienceYears: teacher.anneesExperience,
          qualification: teacher.qualification,
          idEmploye: teacher.idEmploye
        }))
        setTeachers(formattedTeachers)
      }
    } catch (error) {
      console.error("Error fetching data:", error)
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (ids) => {
    setSelectedIds(ids)
    setIsDeleteDialogOpen(true)
  }

  const confirmDelete = async () => {
    try {
      const endpoint = tableType === "students" ? "eleves" : "enseignants"
      await Promise.all(
        selectedIds.map(id => 
          axios.delete(`${import.meta.env.VITE_API_URL}/api/${endpoint}/${id}`)
        )
      )
      await fetchData()
      setIsDeleteDialogOpen(false)
      setSelectedIds([])
    } catch (error) {
      console.error("Error deleting:", error)
    }
  }

  const handleEdit = (id) => {
    if (tableType === "students") {
      const studentToEdit = students.find((student) => student.id === id)
      setEditingStudent(studentToEdit)
      setIsEditStudentDialogOpen(true)
    } else {
      const teacherToEdit = teachers.find((teacher) => teacher.id === id)
      setEditingTeacher(teacherToEdit)
      setIsEditTeacherDialogOpen(true)
    }
  }

  const handleSaveStudent = async (updatedStudent) => {
    try {
      await axios.put(
        `${import.meta.env.VITE_API_URL}/api/eleves/${updatedStudent.id}`,
        {
          idEleve: updatedStudent.idEleve,
          nom: updatedStudent.name.split(" ")[1],
          prenom: updatedStudent.name.split(" ")[0],
          niveau: updatedStudent.level,
          classe: updatedStudent.class,
          dateInscription: `${updatedStudent.yearOfInscription}-01-01`
        }
      )
      await fetchData()
    } catch (error) {
      console.error("Error updating student:", error)
    }
  }

  const handleSaveTeacher = async (updatedTeacher) => {
    try {
      await axios.put(
        `${import.meta.env.VITE_API_URL}/api/enseignants/${updatedTeacher.id}`,
        {
          idEmploye: updatedTeacher.idEmploye,
          nom: updatedTeacher.name.split(" ")[1],
          prenom: updatedTeacher.name.split(" ")[0],
          matiere: updatedTeacher.subject,
          qualification: updatedTeacher.qualification,
          anneesExperience: updatedTeacher.experienceYears
        }
      )
      await fetchData()
    } catch (error) {
      console.error("Error updating teacher:", error)
    }
  }

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
      accessorKey: "name",
      header: "Name",
    },
    {
      accessorKey: "email",
      header: "Email",
    },
    {
      accessorKey: "level",
      header: "Level",
    },
    {
      accessorKey: "age",
      header: "Age",
    },
    {
      accessorKey: "yearOfInscription",
      header: "Year of Inscription",
    },
    {
      accessorKey: "class",
      header: "Class",
    },
  ]

  const teacherColumns = [
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
      accessorKey: "name",
      header: "Name",
    },
    {
      accessorKey: "email",
      header: "Email",
    },
    {
      accessorKey: "subject",
      header: "Subject",
    },
    {
      accessorKey: "experienceYears",
      header: "Experience (Years)",
    },
    {
      accessorKey: "qualification",
      header: "Qualification",
    },
  ]



  return (
    <SidebarProvider>
    <div className="flex min-h-screen dark:bg-background">
      <AppSidebar className="border-r border-gray-300 shadow-[2px_0_10px_rgb(107,114,128)]" />
      <SidebarInset className="flex-1 ">
        <div className="flex flex-col bg-gray-200 h-full">
          <Header />
                <div className="p-6">
                <div className="flex justify-between items-center mb-8">
                    <div>
                    <h1 className="text-2xl font-bold">{tableType === "students" ? "Student" : "Teacher"} List</h1>
                    <p className="text-muted-foreground">Manage your {tableType} and their information here.</p>
                    </div>
                    <div className="flex gap-4">
                    <Button variant="outline" onClick={() => setTableType("students")}>
                        Students
                    </Button>
                    <Button variant="outline" onClick={() => setTableType("teachers")}>
                        Teachers
                    </Button>
                    <Button
                        onClick={() => (tableType === "students" ? setIsStudentDialogOpen(true) : setIsTeacherDialogOpen(true))}
                    >
                        Add {tableType === "students" ? "Student" : "Teacher"}
                    </Button>
                    </div>
                </div>

                {tableType === "students" ? (
                    <DataTable
                    columns={studentColumns}
                    data={students}
                    filterColumn="name"
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    />
                ) : (
                    <DataTable
                    columns={teacherColumns}
                    data={teachers}
                    filterColumn="name"
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    />
                )}

                <AddStudentDialog open={isStudentDialogOpen} onOpenChange={setIsStudentDialogOpen} />
                <AddTeacherDialog open={isTeacherDialogOpen} onOpenChange={setIsTeacherDialogOpen} />
                <EditStudentDialog
                    open={isEditStudentDialogOpen}
                    onOpenChange={setIsEditStudentDialogOpen}
                    student={editingStudent}
                    onSave={handleSaveStudent}
                />
                <EditTeacherDialog
                    open={isEditTeacherDialogOpen}
                    onOpenChange={setIsEditTeacherDialogOpen}
                    teacher={editingTeacher}
                    onSave={handleSaveTeacher}
                />
                <DeleteConfirmationDialog
                    isOpen={isDeleteDialogOpen}
                    onClose={() => setIsDeleteDialogOpen(false)}
                    onConfirm={confirmDelete}
                    itemType={tableType === "students" ? "student" : "teacher"}
                    itemCount={selectedIds.length}
                />
                </div>
        </div>
        </SidebarInset>
            </div>
        </SidebarProvider>
  )
}

