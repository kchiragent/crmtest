// WARNING: This code is auto-generated from the BaseCRM API Discovery JSON Schema

package com.getbase;

import com.getbase.http.HttpClient;
import com.getbase.services.*;
import com.getbase.sync.SyncService;

public class Client {

    private final Configuration config;
    private final HttpClient httpClient;

    private AccountsService accountsService;
    private AssociatedContactsService associatedContactsService;
    private ContactsService contactsService;
    private DealsService dealsService;
    private LeadsService leadsService;
    private LossReasonsService lossReasonsService;
    private NotesService notesService;
    private PipelinesService pipelinesService;
    private SourcesService sourcesService;
    private LeadSourcesService leadSourcesService;
    private StagesService stagesService;
    private TagsService tagsService;
    private TasksService tasksService;
    private UsersService usersService;
    private ProductsService productsService;
    private OrdersService ordersService;
    private LineItemsService lineItemsService;
    private SyncService syncService;

    public Client(Configuration config) {
        this(config, new com.getbase.http.jersey.HttpClient(config));
    }

    public Client(Configuration config, HttpClient httpClient) {
        this.config = config;
        this.httpClient = httpClient;
    }

    public Configuration getConfig() {
        return this.config;
    }

    public HttpClient getHttpClient() {
        return this.httpClient;
    }
   
    public AccountsService accounts() {
        if (this.accountsService == null) {
            this.accountsService = new AccountsService(this.httpClient);
        }
        return this.accountsService;
    }
   
    public AssociatedContactsService associatedContacts() {
        if (this.associatedContactsService == null) {
            this.associatedContactsService = new AssociatedContactsService(this.httpClient);
        }
        return this.associatedContactsService;
    }
   
    public ContactsService contacts() {
        if (this.contactsService == null) {
            this.contactsService = new ContactsService(this.httpClient);
        }
        return this.contactsService;
    }
   
    public DealsService deals() {
        if (this.dealsService == null) {
            this.dealsService = new DealsService(this.httpClient);
        }
        return this.dealsService;
    }
   
    public LeadsService leads() {
        if (this.leadsService == null) {
            this.leadsService = new LeadsService(this.httpClient);
        }
        return this.leadsService;
    }
   
    public LossReasonsService lossReasons() {
        if (this.lossReasonsService == null) {
            this.lossReasonsService = new LossReasonsService(this.httpClient);
        }
        return this.lossReasonsService;
    }
   
    public NotesService notes() {
        if (this.notesService == null) {
            this.notesService = new NotesService(this.httpClient);
        }
        return this.notesService;
    }
   
    public PipelinesService pipelines() {
        if (this.pipelinesService == null) {
            this.pipelinesService = new PipelinesService(this.httpClient);
        }
        return this.pipelinesService;
    }
   
    public SourcesService sources() {
        if (this.sourcesService == null) {
            this.sourcesService = new SourcesService(this.httpClient);
        }
        return this.sourcesService;
    }

    public LeadSourcesService leadSources() {
        if (this.leadSourcesService == null) {
            this.leadSourcesService = new LeadSourcesService(this.httpClient);
        }
        return this.leadSourcesService;
    }
   
    public StagesService stages() {
        if (this.stagesService == null) {
            this.stagesService = new StagesService(this.httpClient);
        }
        return this.stagesService;
    }
   
    public TagsService tags() {
        if (this.tagsService == null) {
            this.tagsService = new TagsService(this.httpClient);
        }
        return this.tagsService;
    }
   
    public TasksService tasks() {
        if (this.tasksService == null) {
            this.tasksService = new TasksService(this.httpClient);
        }
        return this.tasksService;
    }
   
    public UsersService users() {
        if (this.usersService == null) {
            this.usersService = new UsersService(this.httpClient);
        }
        return this.usersService;
    }
   
    public ProductsService products() {
        if (this.productsService == null) {
            this.productsService = new ProductsService(this.httpClient);
        }
        return this.productsService;
    }

    public OrdersService orders() {
        if (this.ordersService == null) {
            this.ordersService = new OrdersService(this.httpClient);
        }
        return this.ordersService;
    }

    public LineItemsService lineItems() {
        if (this.lineItemsService == null) {
            this.lineItemsService = new LineItemsService(this.httpClient);
        }
        return this.lineItemsService;
    }

    public SyncService sync() {
        if (this.syncService == null) {
            this.syncService = new SyncService(this.httpClient);
        }
        return this.syncService;
    }

}
