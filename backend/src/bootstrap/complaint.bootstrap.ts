import Complaint from '../models/complaint.model'
import { Bootstrapper } from './Bootstraper'
export default class ComplaintBootstrap extends Bootstrapper {
  async run() {
    Complaint.bulkCreate([
      {
        complaintId: 'compl-1234-efgh-0000',
        userId: '8de45630-2e76-4d97-98c2-9ec0d1f3a5b8',
        companyId: 'c1b0e7e0-0b1a-4e1a-9f1a-0e5a9a1b0e7e',
        complaintSubject: 'Productos Defectuosos',
        complaintDescription: 'El vendedor me insultó',
        complaintStatus: 'active',
      },
      {
        complaintId: 'compl-1235-efgh-0000',
        userId: '8de45630-2e76-4d97-98c2-9ec0d1f3a5b8',
        companyId: 'c1b0e7e0-0b1a-4e1a-9f1a-0e5a9a1b0e7e',
        complaintSubject: 'Inconformidad con el producto / servicio',
        complaintDescription: 'El vendedor me insultó',
        complaintStatus: 'active',
      },
      {
        complaintId: 'compl-1236-efgh-0000',
        userId: '8de45630-2e76-4d97-98c2-9ec0d1f3a5b8',
        companyId: 'c1b0e7e0-0b1a-4e1a-9f1a-0e5a9a1b0e7e',
        complaintSubject: 'Comportamiento Inapropiado',
        complaintDescription: 'El vendedor me insultó',
        complaintStatus: 'active',
      },
      {
        complaintId: 'compl-1237-efgh-0000',
        userId: '8de45630-2e76-4d97-98c2-9ec0d1f3a5b8',
        companyId: 'c1b0e7e0-0b1a-4e1a-9f1a-0e5a9a1b0e7e',
        complaintSubject: 'Mal Servicio',
        complaintDescription: 'aaaaaaaaaaaaaaaaaaaaaaaaaa',
        complaintStatus: 'active',
      },
    ])
  }
}
